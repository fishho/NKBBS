package com.cfish.rvb.bean;

/**
 * Created by dfish on 2016/12/5.
 */
public class SiteReply {
    private String s_r_id;
    private String user_id;
    private String s_a_id;
    private String p_s_r_id;
    private String creatime;
    private String content;
    private Author user;

    public String getS_r_id() {
        return s_r_id;
    }

    public void setS_r_id(String s_r_id) {
        this.s_r_id = s_r_id;
    }

    public String getS_a_id() {
        return s_a_id;
    }

    public void setS_a_id(String s_a_id) {
        this.s_a_id = s_a_id;
    }

    public String getP_s_r_id() {
        return p_s_r_id;
    }

    public void setP_s_r_id(String p_s_r_id) {
        this.p_s_r_id = p_s_r_id;
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

    public Author getUser() {
        return user;
    }

    public void setUser(Author user) {
        this.user = user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
