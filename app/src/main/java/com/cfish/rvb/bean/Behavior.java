package com.cfish.rvb.bean;

/**
 * Created by GKX100217 on 2016/2/29.
 */
public class Behavior {
/*    //user id
    private String uid;
    //..
    private String group_site_user;

    private String creatime;*/
    //article id ,null is allowed
    private String article;
    //status code
    private String status;
    //group name
    private String name;
    //""is allowed
    private String article_name;
    //like 很久前
    private String time;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
