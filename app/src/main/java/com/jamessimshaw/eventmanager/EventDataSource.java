package com.jamessimshaw.eventmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by james on 3/5/15.
 */
public class EventDataSource {
    private EventLocalDBHelper mEventLocalDBHelper;

    public static final int TODAY = 1;
    public static final int SEVEN_DAYS = 2;
    public static final int THIRTY_DAYS = 3;
    public static final int ALL_FUTURE = 4;

    public EventDataSource(Context context) {
        mEventLocalDBHelper = new EventLocalDBHelper(context);
    }

    private SQLiteDatabase open() {
        return mEventLocalDBHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase database) {
        database.close();
    }

    public void create(Event event) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues eventValues = new ContentValues();
        eventValues.put(EventLocalDBHelper.COLUMN_TITLE, event.getTitle());
        eventValues.put(EventLocalDBHelper.COLUMN_DATE, event.getDate());
        eventValues.put(EventLocalDBHelper.COLUMN_LOCATION, event.getLocation());
        eventValues.put(EventLocalDBHelper.COLUMN_COMMENTS, event.getComments());
        event.setId(database.insert(EventLocalDBHelper.TABLE_EVENTS, null, eventValues));

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public ArrayList<Event> read(int modifier) {
        SQLiteDatabase database = open();
        String endDay;

        switch(modifier) {
            case TODAY:
                break;
            case SEVEN_DAYS:
                break;
            case THIRTY_DAYS:
                break;
            case ALL_FUTURE:
                break;
        }

        String where = "? BETWEEN datetime(Today) AND datetime(?)";

        Cursor cursor = database.query(EventLocalDBHelper.TABLE_EVENTS,
                new String[] {BaseColumns._ID,
                        EventLocalDBHelper.COLUMN_TITLE,
                        EventLocalDBHelper.COLUMN_DATE,
                        EventLocalDBHelper.COLUMN_LOCATION,
                        EventLocalDBHelper.COLUMN_COMMENTS},
                null,
                null,
                null,
                null,
                EventLocalDBHelper.COLUMN_DATE);

        ArrayList<Event> events = new ArrayList<Event>();
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event(getIntFromColumnName(cursor, BaseColumns._ID),
                        getStringFromColumnName(cursor, EventLocalDBHelper.COLUMN_TITLE),
                        getStringFromColumnName(cursor, EventLocalDBHelper.COLUMN_DATE),
                        getStringFromColumnName(cursor, EventLocalDBHelper.COLUMN_LOCATION),
                        getStringFromColumnName(cursor, EventLocalDBHelper.COLUMN_COMMENTS));

                events.add(event);
            } while(cursor.moveToNext());
        }
        cursor.close();
        close(database);
        return events;
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
