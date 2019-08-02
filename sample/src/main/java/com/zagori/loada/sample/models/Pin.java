package com.zagori.loada.sample.models;

public class Pin {

    private Urls urls;
    private int likes;
    private User user;

    public Pin() {
    }

    public Pin(Urls urls, int likes, User user) {
        this.urls = urls;
        this.likes = likes;
        this.user = user;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
