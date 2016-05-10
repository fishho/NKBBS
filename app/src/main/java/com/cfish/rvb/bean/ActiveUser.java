package com.cfish.rvb.bean;

/**
 * Created by GKX100217 on 2016/4/1.
 */
public class ActiveUser {
    private String Name;
    private String gid;
    private String active;
    private String imgSrc;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
