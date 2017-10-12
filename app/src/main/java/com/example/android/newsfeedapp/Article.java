package com.example.android.newsfeedapp;

/**
 * Created by Sabina on 10/5/2017.
 */

public class Article {

    private String title;
    private String author;
//    private Bitmap thumbnail;

    public Article(String title, String author) {
        this.title = title;
        this.author = author;
//        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

//    public Bitmap getThumbnail() {
//        return thumbnail;
//    }

}
