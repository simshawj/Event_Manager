package com.jamessimshaw.eventmanager;

/**
 * Created by james on 3/2/15.
 */
public class Event {
    long mId;
    String mDate;
    String mTitle;
    String mComments;
    String mLocation;

    public Event(long id, String title, String date, String location, String comments) {
        mLocation = location;
        mId = id;
        mDate = date;
        mTitle = title;
        mComments = comments;
    }

    public Event(String title, String date, String location, String comments) {
        mDate = date;
        mTitle = title;
        mComments = comments;
        mLocation = location;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

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
