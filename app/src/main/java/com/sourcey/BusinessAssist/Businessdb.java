package com.sourcey.BusinessAssist;

/**
 * Created by rsant on 13-11-2016.
 */

public class Businessdb {
    private String _id;
    private String name;
    private int review_count;
    private Double rating;
    private String pwd;

    public Businessdb(String _id, String name, int review_count, Double rating,String pwd) {
        this._id = _id;
        this.name = name;
        this.review_count = review_count;
        this.rating = rating;
        this.pwd=pwd;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}