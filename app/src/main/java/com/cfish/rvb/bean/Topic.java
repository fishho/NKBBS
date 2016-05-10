package com.cfish.rvb.bean;

public class Topic {
	//name of topic
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
	private String gid = "";
	//user id
	private String user_id = "";
	private String TopTen = "";
	private String top = "";
	private String ever_TopTen = "";
	private String anonymous = "";
	private String click_num = "";
	private String fav_num = "";
	private String recommend_num = "";
	private String deter_num = "";
	
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
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTopTen() {
		return TopTen;
	}

	public void setTopTen(String topTen) {
		TopTen = topTen;
	}

	public String getFav_num() {
		return fav_num;
	}

	public void setFav_num(String fav_num) {
		this.fav_num = fav_num;
	}

	public String getClick_num() {
		return click_num;
	}

	public void setClick_num(String click_num) {
		this.click_num = click_num;
	}

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public String getEver_TopTen() {
		return ever_TopTen;
	}

	public void setEver_TopTen(String ever_TopTen) {
		this.ever_TopTen = ever_TopTen;
	}

	public String getRecommend_num() {
		return recommend_num;
	}

	public void setRecommend_num(String recommend_num) {
		this.recommend_num = recommend_num;
	}

	public String getDeter_num() {
		return deter_num;
	}

	public void setDeter_num(String deter_num) {
		this.deter_num = deter_num;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	@Override
	public String toString() {
		return "Topic{" +
				"name='" + name + '\'' +
				", groupname='" + groupname + '\'' +
				", creatime='" + creatime + '\'' +
				", author='" + author + '\'' +
				", reply_num='" + reply_num + '\'' +
				", g_a_id='" + g_a_id + '\'' +
				", gid='" + gid + '\'' +
				'}';
	}
}

