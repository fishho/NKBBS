package com.cfish.rvb.bean;

import java.util.ArrayList;

/**
 * Created by GKX100217 on 2016/4/14.
 */
public class Message {
    private String ajax_status;
    private ArrayList<News> news;
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public String getAjax_status() {
        return ajax_status;
    }

    public void setAjax_status(String ajax_status) {
        this.ajax_status = ajax_status;
    }

    public class News {
        private  String uid;
        private  String num;
        private  String status;
        private  String history_status;
        private  String id ;
        private  String reply_id;
        private  String ctime;
        private  String count;
        private  String name;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHistory_status() {
            return history_status;
        }

        public void setHistory_status(String history_status) {
            this.history_status = history_status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
