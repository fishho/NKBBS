package com.cc.dfish.nkbbs.bean;

/**
 * Created by dfish on 2016/2/3.
 */
public class Topic {

    private String name = "";
    //Name of group
    private String groupname = "";
    //create time of the topic
    private String creatime  = "";
    //author of the topic
    private String author = "";
    //num of reply
    private String reply_num = "";
    //unique id
    private String g_a_id = "";
    //url of headimage
    private String url = "";

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    public String getCreatime() {
        return creatime;
    }
    public void setCreatime(String creatime) {
        this.creatime = creatime;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getReply_num() {
        return reply_num;
    }
    public void setReply_num(String reply_num) {
        this.reply_num = reply_num;
    }
    public String getG_a_id() {
        return g_a_id;
    }
    public void setG_a_id(String g_a_id) {
        this.g_a_id = g_a_id;
    }
    @Override
    public String toString() {
        return "Topic [name=" + name + ", groupname=" + groupname
                + ", creatime=" + creatime + ", author=" + author
                + ", reply_num=" + reply_num + ", g_a_id=" + g_a_id + "]";
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = "http://bbs.nankai.edu.cn/data/uploads/avatar/"+url;
    }

}

