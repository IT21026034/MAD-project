package com.example.hospitalmanagementsystem;

public class ReviewModel {
    public String id;
    public  String reviewedBy;
    public  String reviewedTo;
    public String review;
    public int stars;

    public ReviewModel(String id, String reviewedBy, String reviewedTo, String review, int stars) {
        this.id = id;
        this.reviewedBy = reviewedBy;
        this.reviewedTo = reviewedTo;
        this.review = review;
        this.stars = stars;
    }
}
