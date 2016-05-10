package com.example.android.popularmovies_stage2.DataObjects;

/**
 * Created by mellysamargaritasusilo on 5/9/16.
 */
public class MovieReview {

    private String author;
    private String content;

    public MovieReview(String author, String content){
        this.author = author;
        this.content = content;;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

}