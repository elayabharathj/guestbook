package com.guestbook.guestapp.model;

import javax.persistence.*;

@Entity
@Table(name="post")
public class Post {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private String title;
    private String body;
    private boolean approvedStatus;

    public Post() {
    }

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(boolean approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }



}
