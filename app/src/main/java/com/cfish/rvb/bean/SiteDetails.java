package com.cfish.rvb.bean;

import java.util.List;

/**
 * Created by dfish on 2016/12/5.
 */
public class SiteDetails {
    private SiteArticle article;
    private List<SiteReply> reply;
    private Author author;

    public SiteArticle getArticle() {
        return article;
    }

    public void setArticle(SiteArticle article) {
        this.article = article;
    }

    public List<SiteReply> getReply() {
        return reply;
    }

    public void setReply(List<SiteReply> reply) {
        this.reply = reply;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
