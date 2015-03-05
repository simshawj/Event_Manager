package com.jamessimshaw.eventmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by james on 3/4/15.
 */
public class EventLocalDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "events.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_COMMENTS = "comments";

    private static final String DB_CREATE = "create table " +
            TABLE_EVENTS + " (" + BaseColumns._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE +
            " TEXT NOT NULL, " + COLUMN_DATE +
            " TEXT NOT NULL, " + COLUMN_LOCATION +
            " TEXT NOT NULL, " + COLUMN_COMMENTS +
            " TEXT)";


    public EventLocalDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
