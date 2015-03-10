package com.jamessimshaw.eventmanager.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.jamessimshaw.eventmanager.R;
import com.jamessimshaw.eventmanager.models.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by james on 3/5/15.
 */
public class EventDataSource {
    public static final String TAG = "EventLocalDBHelper";
    public static final int TODAY = 1;
    public static final int SEVEN_DAYS = 2;
    public static final int THIRTY_DAYS = 3;
    public static final int ALL_FUTURE = 4;

    private EventLocalDBHelper mEventLocalDBHelper;
    private Context mContext;

    public EventDataSource(Context context) {
        mContext = context;
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

    public void update(Event event) {
        if (event.getId() < 0)
            return;

        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues eventValues = new ContentValues();
        eventValues.put(EventLocalDBHelper.COLUMN_TITLE, event.getTitle());
        eventValues.put(EventLocalDBHelper.COLUMN_DATE, event.getDate());
        eventValues.put(EventLocalDBHelper.COLUMN_LOCATION, event.getLocation());
        eventValues.put(EventLocalDBHelper.COLUMN_COMMENTS, event.getComments());
        database.update(EventLocalDBHelper.TABLE_EVENTS, eventValues, BaseColumns._ID + "=" +
                Long.toString(event.getId()), null);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public ArrayList<Event> read(int modifier) {
        SQLiteDatabase database = open();
        String where = EventLocalDBHelper.COLUMN_DATE + " between ? and ?";
        String[] whereArgs = new String[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat(mContext.getString(R.string.dateFormatString));
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        String today = dateFormat.format(calendar.getTime());
        whereArgs[0] = today;

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);

        switch(modifier) {
            case TODAY:
                whereArgs[1] = dateFormat.format(calendar.getTime());
                break;
            case SEVEN_DAYS:
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                whereArgs[1] = dateFormat.format(calendar.getTime());;
                break;
            case THIRTY_DAYS:
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                whereArgs[1] = dateFormat.format(calendar.getTime());;
                break;
            case ALL_FUTURE:
                where = EventLocalDBHelper.COLUMN_DATE + " >= ?";
                whereArgs = new String[1];
                whereArgs[0] = today;
                break;
            default:
                where = null;
                whereArgs = null;
                break;
        }


        Cursor cursor = database.query(EventLocalDBHelper.TABLE_EVENTS,
                new String[] {BaseColumns._ID,
                        EventLocalDBHelper.COLUMN_TITLE,
                        EventLocalDBHelper.COLUMN_DATE,
                        EventLocalDBHelper.COLUMN_LOCATION,
                        EventLocalDBHelper.COLUMN_COMMENTS},
                where,
                whereArgs,
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
