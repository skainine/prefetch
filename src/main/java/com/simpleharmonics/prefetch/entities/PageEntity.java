package com.simpleharmonics.prefetch.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "pages")
public class PageEntity {

    @Id
    @Column(name = "link", columnDefinition = "text")
    public String link;

    @Column(name = "title", columnDefinition = "text")
    public String title;

    @Column(name = "body", columnDefinition = "text")
    public String body;

    @CreationTimestamp
    @Column(name = "created")
    public Timestamp created;

    @UpdateTimestamp
    @Column(name = "updated")
    public Timestamp updated;

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }
}
