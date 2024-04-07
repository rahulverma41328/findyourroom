package com.example.findyourroom.HelperClass;

public class AllPostItemsHelper {
    private String postId;
    private String postImage;
    private String postDescription;
    private String postedBy;
    private long postedAt;
    private String description;
    private String username;
    private String rate;
    private String profilePhoto;
    private String phoneNo;
    private String location;


    public AllPostItemsHelper(){}

    public AllPostItemsHelper(String postId, String postImage, String postDescription,
                              String postedBy, long postedAt, String username,String profilePhoto,String location,
                              String phoneNo,String description,String rate) {
        this.postId = postId;
        this.postImage = postImage;
        this.postDescription = postDescription;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.username = username;
        this.profilePhoto = profilePhoto;
        this.location = location;
        this.phoneNo = phoneNo;
        this.description = description;
        this.rate = rate;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
