package com.guestbook.guestapp.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name="entry")
public class Post {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private String title;
    private String body;
    private boolean approvedStatus;

    //Maps to form
    @Transient
    private MultipartFile file;

    private String name;
    //Base64 encoded string to render the image in the edit form
    @Transient
    private String download;

    @Lob
    private byte[] content;

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


    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
}
