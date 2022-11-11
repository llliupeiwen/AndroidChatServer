package com.example.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private int id;
    private String target;
    private String user;
    private String content;
    private String time;

    public void setId(int id){this.id=id;}
    public int getId(){return id;}
    public void setTarget(String target){this.target=target;}
    public String getTarget(){return target;}
    public void setUser(String user){this.user=user;}
    public String getUser(){return user;}
    public void setContent(String content){this.content=content;}
    public String getContent(){return content;}
    public void setTime(String time){this.time=time;}
    public String getTime(){return time;}

    public int compareTo(Message m) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        if(this == m){
            return 0;
        }else if(m!=null){
            ;
            if(sdf.parse(m.getTime()).getTime()<sdf.parse(this.time).getTime())
                return 1;
            else
                return -1;
        } else {
            return -1;
        }
    }
}
