package com.example.service;

import com.example.pojo.ContactList;
import com.example.pojo.Message;
import com.example.pojo.User;
import com.example.pojo.ChatList;

import java.util.ArrayList;
import java.util.Date;

public interface UserService {
    //注册用户
    int reigisterUser(String number, String name, String phone, String password);

    //用户登录
    User login(String number,String password);

    //消息列表
    ArrayList<ChatList> informationUser(String number);

    //通讯录
    ArrayList<ContactList> contact(String number);

    //存储最近消息
    int saveInformation(String titleimg, String title, String content, Date time, String number, String target);

    //存储历史消息
    int saveMessage(String target, String user,String content, Date time);

    //历史消息
    ArrayList<Message> historicalMessage(String target, String user);

    //删除好友
    int deleteFriend(String number,String friend);
}
