package com.zagori.loada.sample.models;

public class User {

    private String id;
    private String username;
    private String name;
    private ProfileImage profile_image;

    public User() {
    }

    public User(String id, String username, String name, ProfileImage profile_image) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.profile_image = profile_image;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileImage getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(ProfileImage profile_image) {
        this.profile_image = profile_image;
    }
}
