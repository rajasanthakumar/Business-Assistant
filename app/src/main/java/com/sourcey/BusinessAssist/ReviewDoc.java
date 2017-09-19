package com.sourcey.BusinessAssist;

/**
 * Created by rsant on 13-11-2016.
 */

public class ReviewDoc {
    private String _id;
    private String excerpt;
    private double rating;
    private long time_created;

    public ReviewDoc(String _id,String excerpt, double rating, long time_created) {
        this._id=_id;
        this.excerpt = excerpt;
        this.rating = rating;
        this.time_created = time_created;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getTime_created() {
        return time_created;
    }

    public void setTime_created(long time_created) {
        this.time_created = time_created;
    }
}
