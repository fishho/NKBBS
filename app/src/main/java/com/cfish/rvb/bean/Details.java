package com.cfish.rvb.bean;

import java.util.List;

/**
 * Created by GKX100217 on 2016/3/24.
 */
public class Details {
    private Article article;
    private List<Reply> reply;
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
