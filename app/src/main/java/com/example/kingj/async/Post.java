package com.example.kingj.async;

public class Post {

    String post_title;
    long post_id;

    public Post(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }
}
