package com.jamessimshaw.eventmanager;

/**
 * Created by james on 3/2/15.
 */
public class Event {
    String mDate;
    String mTitle;
    String mComments;
    String mLocation;       //Maybe use a different data type for locations

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getComments() {
        return mComments;
    }

    public void setComments(String comments) {
        mComments = comments;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }


}
