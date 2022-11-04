package com.example.service;

import com.example.pojo.ContactList;
import com.example.pojo.User;
import com.example.pojo.ChatList;

import java.util.ArrayList;

public interface UserService {
    //注册用户
    int reigisterUser(String number, String name, String phone, String password);

    //用户登录
    User login(String number,String password);

    //消息列表
    ArrayList<ChatList> informationUser(String number);

    //通讯录
    ArrayList<ContactList> contact(String number);
}
