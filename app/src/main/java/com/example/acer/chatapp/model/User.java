package com.example.acer.chatapp.model;

public class User {

    private String id;
    private String username;
    private String imageurl;
    private String status;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)


    public User(String id, String username, String imageurl,String status) {
        this.id = id;
        this.username = username;
        this.imageurl = imageurl;
        this.status=status;
    }
    public User()
    {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
