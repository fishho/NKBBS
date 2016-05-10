package com.cfish.rvb.bean;

import java.util.List;

/**
 * Created by GKX100217 on 2016/2/29.
 */
public class Article {
    private String gid;
    private String name;
    private String reply_num;
    private String fav_num;
    private String click_num;
    private String recommend_num;
    private String recommend_user;
    private String deter_num;
    private String creatime;
    private String content;
    private String status;
    private String marrow;
    private String can_reply;
    private String Top_Ten;
    private String ever_top;
    private String anonymous;
    private List<Tag> g_a_tags;

    public String getClick_num() {
        return click_num;
    }

    public void setClick_num(String click_num) {
        this.click_num = click_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReply_num() {
        return reply_num;
    }

    public void setReply_num(String reply_num) {
        this.reply_num = reply_num;
    }

    public String getFav_num() {
        return fav_num;
    }

    public void setFav_num(String fav_num) {
        this.fav_num = fav_num;
    }

    public String getRecommend_num() {
        return recommend_num;
    }

    public void setRecommend_num(String recommend_num) {
        this.recommend_num = recommend_num;
    }

    public String getRecommend_user() {
        return recommend_user;
    }

    public void setRecommend_user(String recommend_user) {
        this.recommend_user = recommend_user;
    }

    public String getDeter_num() {
        return deter_num;
    }

    public void setDeter_num(String deter_num) {
        this.deter_num = deter_num;
    }

    public String getCreatime() {
        return creatime;
    }

    public void setCreatime(String creatime) {
        this.creatime = creatime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarrow() {
        return marrow;
    }

    public void setMarrow(String marrow) {
        this.marrow = marrow;
    }

    public String getCan_reply() {
        return can_reply;
    }

    public void setCan_reply(String can_reply) {
        this.can_reply = can_reply;
    }

    public String getTop_Ten() {
        return Top_Ten;
    }

    public void setTop_Ten(String top_Ten) {
        Top_Ten = top_Ten;
    }

    public String getEver_top() {
        return ever_top;
    }

    public void setEver_top(String ever_top) {
        this.ever_top = ever_top;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public List<Tag> getG_a_tags() {
        return g_a_tags;
    }

    public void setG_a_tags(List<Tag> g_a_tags) {
        this.g_a_tags = g_a_tags;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
