package com.cfish.rvb.bean;

import java.util.List;

/**
 * Created by GKX100217 on 2016/2/29.
 */
public class UserInfo {
    private int relate;

    private int related;

    private int msg;

    private int ignore;

    private int ignored;

    private String name;
    //无可奉告，男女皆可
    private String sex;

    private String score;

    private String rank;

    private String level;
    //似乎没有
    private String signature;

    private String college;

    private String province;

    private String city;

    private String birthday;

    private String xingzuo;
    //所有人可见
    private String info_privacy;

    private List<Behavior> behavior ;

    public int getRelate() {
        return relate;
    }

    public void setRelate(int relate) {
        this.relate = relate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getXingzuo() {
        return xingzuo;
    }

    public void setXingzuo(String xingzuo) {
        this.xingzuo = xingzuo;
    }

    public String getInfo_privacy() {
        return info_privacy;
    }

    public void setInfo_privacy(String info_privacy) {
        this.info_privacy = info_privacy;
    }

    public List<Behavior> getBehavior() {
        return behavior;
    }

    public void setBehavior(List<Behavior> behavior) {
        this.behavior = behavior;
    }
}
