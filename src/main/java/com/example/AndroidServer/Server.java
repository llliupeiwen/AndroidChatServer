package com.example.AndroidServer;

import com.example.pojo.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static Map<String,UserThread> clientList=new HashMap<String,UserThread>();
    private static Map<String,String> nameToIp=new HashMap<>();

    public static void main(String[] args) throws IOException {

        System.out.println("服务器启动！");
        new Thread(new startServer()).start();
    }
    private static int port = 6666;
    //public static ArrayList<UserThread> socketList = new ArrayList<UserThread>();

    private static class startServer extends Thread{
        public void run(){
            try {
                //绑定服务器要监听的端口
                ServerSocket serverSocket = new ServerSocket(port);
                while(true){
                    Socket socket = serverSocket.accept();
                    //从队列中取出连接请求，使得队列能及时腾出空位，以容纳新的连接请求
                    System.out.println("socket::||"+socket);
                    //System.out.println("InetAddress::||"+socket.getInetAddress());
                    //System.out.println("HostAddress::||"+socket.getInetAddress().getHostAddress());
                    UserThread userThread = new UserThread(socket);
                    Server.clientList.put(socket.getInetAddress().getHostAddress(),userThread);
                    //socketList.add(userThread);
                    nameToIp.put(new DataInputStream(socket.getInputStream()).readUTF(),socket.getInetAddress().getHostAddress());
                    System.out.println(nameToIp);
                    new Thread(userThread).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class UserThread implements Runnable {
        private Socket skt;
        private DataInputStream dis;
        private DataOutputStream dos;

        public DataOutputStream getDos() {
            return dos;
        }

        public UserThread(Socket socket) {
            skt = socket;
        }

        //接收send过来的线程
        @Override
        public void run() {
            try {
                dos = new DataOutputStream(skt.getOutputStream());
                dis = new DataInputStream(skt.getInputStream());
                while (true) {
                    String r_content ;
                    String name ;
                    String time ;
                    String imageId ;
                    String his_name;
                    time = dis.readUTF();
                    r_content = dis.readUTF();
                    name = dis.readUTF();
                    imageId = dis.readUTF();
                    his_name = dis.readUTF();
                    System.out.println("content = "+r_content);
                    if(time == null){
                        continue;
                    }

                    //发送出去
                    UserThread ut = Server.clientList.get(nameToIp.get(his_name));
                    try{
                        System.out.println(time);
                        //writeUTF在写入数据流的时候会加上两个字节以表示字节的长度
                        ut.getDos().writeUTF(time);
                        ut.getDos().writeUTF(r_content);
                        ut.getDos().writeUTF(name);
                        ut.getDos().writeUTF(imageId);
                        //刷新
                        ut.getDos().flush();
                    }catch(Exception e){
                        Server.clientList.remove(ut);
                        e.printStackTrace();
                    }
                    /*
                    for(UserThread ut : socketList){
                        if(ut.equals(this)){  //是自己的消息就不发出
                            System.out.println("自己的");
                            continue;
                        }
                        try{
                            System.out.println(time);
                            //writeUTF在写入数据流的时候会加上两个字节以表示字节的长度
                            ut.getDos().writeUTF(time);
                            ut.getDos().writeUTF(r_content);
                            ut.getDos().writeUTF(name);
                            ut.getDos().writeUTF(imageId);
                            System.out.println("写出去");
                            //刷新
                            ut.getDos().flush();
                        }catch(Exception e){
                            socketList.remove(ut);
                            e.printStackTrace();
                        }
                    }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


