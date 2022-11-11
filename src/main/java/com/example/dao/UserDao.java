package com.example.dao;

import com.example.pojo.ChatList;
import com.example.pojo.ContactList;
import com.example.pojo.Message;
import com.example.pojo.User;

import java.util.ArrayList;
import java.util.Date;

public interface UserDao {
    //添加用户
    int insertUser(String number, String name, String phone, String password);

    //查询用户通过微信号
    User findByUsername(String number);

    //查询消息列表
    ArrayList<ChatList> findInformation(String number);

    //存储最近记录
    int insertInformation(String titleimg, String title, String content, Date time, String number, String target);

    //查询通信录列表
    ArrayList<ContactList> findContact(String number);

    //存储历史记录
    int insertMessage(String target, String user, String content, Date time);

    //查找历史记录
    ArrayList<Message> findMessage(String target, String user);
}
