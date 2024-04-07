package com.example.findyourroom.HelperClass;

public class UserPostItemsHelper {

    private String title,description,profilePhoto,location;

    public UserPostItemsHelper(){

    }

    public UserPostItemsHelper(String title, String description, String profilePhoto,String location) {
        this.title = title;
        this.description = description;
        this.profilePhoto = profilePhoto;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
