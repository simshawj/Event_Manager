package com.jamessimshaw.eventmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by james on 3/5/15.
 */
public class EventDataSource {
    private EventLocalDBHelper mEventLocalDBHelper;

    public EventDataSource(Context context) {
        mEventLocalDBHelper = new EventLocalDBHelper(context);
        SQLiteDatabase database = mEventLocalDBHelper.getReadableDatabase();
        database.close();
    }

    private SQLiteDatabase open() {
        return mEventLocalDBHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase database) {
        database.close();
    }

    private void create(Event event) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues eventValues = new ContentValues();
        eventValues.put(EventLocalDBHelper.COLUMN_TITLE, event.getTitle());
        eventValues.put(EventLocalDBHelper.COLUMN_DATE, event.getDate().toString());    //TODO: Double check that date.toString returns in the right format or find a different function
        eventValues.put(EventLocalDBHelper.COLUMN_LOCATION, event.getLocation());
        eventValues.put(EventLocalDBHelper.COLUMN_COMMENTS, event.getComments());
        long eventID = database.insert(EventLocalDBHelper.TABLE_EVENTS, null, eventValues);

        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }
}
