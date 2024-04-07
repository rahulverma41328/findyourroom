package com.example.findyourroom.Database;

public class UploadPost {

    private String postId;
    private String postImage;
    private String postDescription;
    private String postedBy;
    private long postedAt;
    private String phoneNo;
    private String location;
    private String rate;

    public UploadPost(String postImage, String postDescription, String postedBy, long postedAt,String phoneNo,String postId,String location,String rate) {
        this.postImage = postImage;
        this.postDescription = postDescription;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.phoneNo = phoneNo;
        this.postId = postId;
        this.location = location;
        this.rate = rate;
    }

    public UploadPost() {
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
