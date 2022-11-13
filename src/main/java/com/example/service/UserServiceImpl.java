package com.example.service;

import com.example.dao.UserDao;
import com.example.dao.UserDaoImpl;
import com.example.pojo.ContactList;
import com.example.pojo.Message;
import com.example.pojo.User;
import com.example.pojo.ChatList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class UserServiceImpl implements UserService {
    UserDao ud = new UserDaoImpl();

    @Override
    public int reigisterUser(String number, String name, String phone, String password) {
        int i = ud.insertUser(number, name, phone, password);
        return i;
    }

    @Override
    public User login(String number, String password) {
        //调用dao层完成数据查询操作
        User user = ud.findByUsername(number);
        if (user != null) {
            //比较密码
            if (password.equals(user.getPassword())) {
                //登录成功
                return user;
            }
        }
        return null;
    }

    public ArrayList<ChatList> informationUser(String number) {
        ArrayList<ChatList> information;
        //调用dao层完成数据查询操作
        information = ud.findInformation(number);

        return information;
    }

    public ArrayList<ContactList> contact(String number) {
        //调用dao层完成数据查询操作
        ArrayList<ContactList> contactList = ud.findContact(number);
        return contactList;
    }

    //存储最近消息
    public int saveInformation(String titleimg, String title, String content, Date time, String number, String target){
        int i=ud.insertInformation(titleimg,title,content,time,number,target);
        return i;
    }

    //存储历史消息
    public int saveMessage(String target, String user, String content, Date time){
        int i=ud.insertMessage(target,user,content,time);
        return i;
    }

    public ArrayList<Message> historicalMessage(String target, String user){
        ArrayList<Message> messages = ud.findMessage(target,user);
        Comparator<Message> comparator = new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                try {
                    return o1.compareTo(o2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
        Collections.sort(messages,comparator);
        return messages;
    }

    @Override
    public int deleteFriend(String number, String friend) {
        int i=ud.deleteContact(number,friend);
        return i;
    }
}
