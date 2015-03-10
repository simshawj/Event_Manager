package com.jamessimshaw.eventmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private long mId;
    private String mDate;
    private String mTitle;
    private String mComments;
    private String mLocation;

    public Event(long id, String title, String date, String location, String comments) {
        mLocation = location;
        mId = id;
        mDate = date;
        mTitle = title;
        mComments = comments;
    }

    public Event(String title, String date, String location, String comments) {
        mId = -1;
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

    private Event(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mLocation = in.readString();
        mDate = in.readString();
        mComments = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mLocation);
        dest.writeString(mDate);
        dest.writeString(mComments);
    }

    public static final Creator CREATOR = new Creator() {

        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
