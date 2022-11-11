package com.example.dao;

import com.example.pojo.ChatList;
import com.example.pojo.ContactList;
import com.example.pojo.Message;
import com.example.pojo.User;
import com.example.util.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDaoImpl implements UserDao {

    ResultSet rs;

    @Override
    public int insertUser(String number, String name, String phone, String password) {
        String sql = "insert into user (number, name, phone, password, remark) values(?,?,?,?,?);";
        //i如果操作成功，就是操作成功的条数
        int i = JDBCUtil.executeUpdate(sql, number, name, phone, password, "1");
        System.out.println("数据库的条数:");
        return i;
    }

    @Override
    public User findByUsername(String number) {
        //判断数据是用户名还是手机
        Pattern pattern = Pattern
                .compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
        Matcher matcher = pattern.matcher(number);
        //手机sql执行语句
        if (matcher.matches()) {
            //sql
            String sql = "select * from user where phone=?";
            rs = JDBCUtil.executeQuery(sql, number);
        } else {  //用户名sql执行语句
            //sql
            String sql = "select * from user where number=?";
            rs = JDBCUtil.executeQuery(sql, number);
        }
        //判断是否查询到用户
        try {
            if (rs.next()) {
                //如果查询到用户，将用户封装到User对象中
                int id = rs.getInt("id");
                String number1 = rs.getString("number");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String remark = rs.getString("remark");
                String img = rs.getString("img");
                //将查询到的用户封装到一个User对象中
                User user = new User();
                user.setId(id);
                user.setNumber(number1);
                user.setName(name);
                user.setPassword(password);
                user.setPhone(phone);
                user.setRemark(remark);
                user.setImg(img);
                System.out.println("查询到的用户" + user);
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int insertInformation(String titleimg, String title, String content, Date time, String number, String target){
        String sql = "insert into chatlist (titleimg, title, content, time,number,target) values(?,?,?,?,?,?);";
        //i如果操作成功，就是操作成功的条数
        int i = JDBCUtil.executeUpdate(sql, titleimg, title, content, time,number,target, "1");
        return i;
    }

    @Override
    public ArrayList<ChatList> findInformation(String number) {
        //sql
        ArrayList<ChatList> resultList = new ArrayList<>();
        String sql = "select * from chatlist where number=?;";
        ResultSet rs = JDBCUtil.executeQuery(sql, number);
        //判断是否查询到用户
        try {
            while (rs.next()) {
                //如果查询到用户，将用户封装到User对象中
                int id = rs.getInt("id");
                String titleimg = rs.getString("titleimg");
                String title1 = rs.getString("title");
                String content = rs.getString("content");
                String time = rs.getString("time");
                String showcode = rs.getString("showcode");
                String number1 = rs.getString("number");
                //将查询到的用户封装到一个User对象中
                ChatList chatList = new ChatList();
                chatList .setId(id);
                chatList .setTitleimg(titleimg);
                chatList .setTitle(title1);
                chatList .setContent(content);
                chatList .setTime(time);
                chatList .setShowcode(showcode);
                chatList .setNumber(number1);
                System.out.println("查询到的用户" + chatList);
                resultList.add(chatList);
            }
            return resultList;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<ContactList> findContact(String number) {

        ArrayList<ContactList> contactLists = new ArrayList<>();
        String sql = "select * from contact where number=?;";
        ResultSet rs = JDBCUtil.executeQuery(sql, number);
        //判断是否查询到用户
        try {
            while (rs.next()) {
                //如果查询到用户，将用户封装到User对象中
                int id = rs.getInt("id");
                String img = rs.getString("img");
                String name = rs.getString("name");
                String number1 = rs.getString("number");
                String friend =rs.getString("friend");
                //将查询到的用户封装到一个User对象中
                ContactList contactList = new ContactList();
                contactList .setId(id);
                contactList .setImg(img);
                contactList .setName(name);
                contactList .setNumber(number1);
                contactList.setFriend(friend);
                System.out.println("查询到的用户" + contactList);
                contactLists.add(contactList);
            }
            return contactLists;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int insertMessage(String target, String user, String content, Date time) {
        String sql = "insert into message (target, user, content, time) values(?,?,?,?);";
        //i如果操作成功，就是操作成功的条数
        int i = JDBCUtil.executeUpdate(sql, target, user, content, time);
        return i;
    }

    public ArrayList<Message> findMessage(String target, String user){
        String sql = "select * from message where (target=?&&user=?)||(target=?&&user=?);";
        ResultSet rs = JDBCUtil.executeQuery(sql,target,user,user,target);
        ArrayList<Message> messages= new ArrayList<>();
        try {
            while (rs.next()){
                int id = rs.getInt("id");
                String target1 = rs.getString("target");
                String user1 = rs.getString("user");
                String content = rs.getString("content");
                String  time = rs.getString("time");
                Message message = new Message();
                message.setId(id);
                message.setTarget(target1);
                message.setUser(user1);
                message.setContent(content);
                message.setTime(time);
                messages.add(message);
            }
            return messages;
        }catch (SQLException throwables){
            throwables.printStackTrace();;
        }
        return null;
    }
}
