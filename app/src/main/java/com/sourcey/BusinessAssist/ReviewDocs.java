package com.sourcey.BusinessAssist;

/**
 * Created by rsant on 13-11-2016.
 */

public class ReviewDocs {
    private String _id;
    private String user_id;
    private String business_id;
    private String review_id;
    private String type;
    private int stars;
    private String date;
    private String text;


    public ReviewDocs(String _id, String user_id, String business_id, String review_id, String type, int stars, String date, String text){
        this._id = _id;
        this.user_id = user_id;
        this.business_id = business_id;
        this.review_id = review_id;
        this.type = type;
        this.stars = stars;
        this.date = date;
        this.text = text;

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
