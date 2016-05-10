package com.cfish.rvb.bean;

/**
 * Created by GKX100217 on 2016/2/26.
 */
public class Reply {
    private String g_r_id;
    private String p_g_r_id;
    private String user_id;
    private String content="";
    private String creatime;
    private String noname;
    private Author user;

    public Author getUser() {
        return user;
    }

    public void setUser(Author user) {
        this.user = user;
    }

    public String getNoname() {
        return noname;
    }

    public void setNoname(String noname) {
        this.noname = noname;
    }

    public String getCreatime() {
        return creatime;
    }

    public void setCreatime(String creatime) {
        this.creatime = creatime;
    }

    public String getContent() {
        return content.replace("quoteblock","blockquote");
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getG_r_id() {
        return g_r_id;
    }

    public void setG_r_id(String g_r_id) {
        this.g_r_id = g_r_id;
    }

    public String getP_g_r_id() {
        return p_g_r_id;
    }

    public void setP_g_r_id(String p_g_r_id) {
        this.p_g_r_id = p_g_r_id;
    }
}
