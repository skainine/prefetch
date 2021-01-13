package com.simpleharmonics.prefetch.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pages")
public class PageEntity {

    @Id
    @Column(name = "link", columnDefinition = "text")
    private String link;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "time", columnDefinition = "text")
    private String time;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String toString() {
        return "Title: " + getTitle() + "\n" +
                "Time: " + getTime() + "\n" +
                "Link: " + getLink() + "\n" +
                "Body: " + getBody() + "\n";
    }
}
