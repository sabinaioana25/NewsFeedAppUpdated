package com.example.android.newsfeedapp;

/**
 * Created by Sabina on 10/5/2017.
 */

public class Article {

    private String title;
    private String section;
    private String publishedDate;
    private String webUrl;


    public Article(String title, String section, String publishedDate, String webUrl) {
        this.title = title;
        this.section = section;
        this.webUrl = webUrl;
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

}
