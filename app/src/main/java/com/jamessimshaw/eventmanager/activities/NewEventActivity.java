package com.jamessimshaw.eventmanager.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jamessimshaw.eventmanager.R;
import com.jamessimshaw.eventmanager.datasources.EventDataSource;
import com.jamessimshaw.eventmanager.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by James on 3/5/2015.
 */
public class NewEventActivity extends Activity {
    private static final String TAG = NewEventActivity.class.getClass().getSimpleName();
    private EditText mTitleEditText;
    private EditText mLocationEditText;
    private EditText mCommentsEditText;
    private Button mSaveButton;
    private Button mCancelButton;
    private int mEventHour;
    private int mEventMinute;
    private int mEventDay;
    private int mEventMonth;
    private int mEventYear;
    private Event mEvent;
    private EventDataSource mEventDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mEventDataSource = new EventDataSource(this);

        mTitleEditText = (EditText) findViewById(R.id.eventTitleEditText);
        mLocationEditText = (EditText) findViewById(R.id.eventLocationEditText);
        mCommentsEditText = (EditText) findViewById(R.id.eventCommentsEditText);
        mSaveButton = (Button) findViewById(R.id.eventSaveButton);
        mCancelButton = (Button) findViewById(R.id.eventCancelButton);

        setDefaultValues();

        mSaveButton.setOnClickListener(mSaveClickListener);
        mCancelButton.setOnClickListener(mCancelClickListener);
    }

    private void setDefaultValues() {
        Intent intent = getIntent();

        if (intent.hasExtra("event")) {
            mEvent = intent.getParcelableExtra("event");
            SimpleDateFormat dateFormat = new SimpleDateFormat(this.getString(R.string.dateFormatString));
            try {
                Date date = dateFormat.parse(mEvent.getDate());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                mEventHour = calendar.get(Calendar.HOUR_OF_DAY);
                mEventMinute = calendar.get(Calendar.MINUTE);
                mEventDay = calendar.get(Calendar.DAY_OF_MONTH);
                mEventMonth = calendar.get(Calendar.MONTH);
                mEventYear = calendar.get(Calendar.YEAR);
                mTitleEditText.setText(mEvent.getTitle(), TextView.BufferType.EDITABLE);
                mLocationEditText.setText(mEvent.getLocation(), TextView.BufferType.EDITABLE);
                mCommentsEditText.setText(mEvent.getComments(), TextView.BufferType.EDITABLE);
            }
            catch (ParseException e) {
                Log.d(TAG, getString(R.string.exceptionCaughtString), e);
                Toast.makeText(this, getString(R.string.editEventLoadErrorToastMessage), Toast.LENGTH_LONG).show();
                setDefaults();
            }
        }
        else {
            setDefaults();
        }
    }

    private void setDefaults() {
        mEvent = null;
        mEventHour = -1;
        mEventMinute = -1;
        mEventDay = -1;
        mEventMonth = -1;
        mEventYear = -1;
    }

    private boolean isValidInput() {
        if (mTitleEditText.getText().toString().equals(""))
            return false;
        if (mLocationEditText.getText().toString().equals(""))
            return false;
        if (mEventMinute < 0 || mEventHour < 0 || mEventDay < 0 || mEventMonth < 0 || mEventYear < 0)
            return false;
        return true;
    }

    public void showDatePicker(View view) {
        int year;
        int month;
        int day;

        if (mEventYear != -1 && mEventMonth != -1 && mEventDay != -1) {
            year = mEventYear;
            month = mEventMonth;
            day = mEventDay;
        }
        else {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mEventDay = dayOfMonth;
                mEventMonth = monthOfYear;
                mEventYear = year;
            }
        }, year, month, day);
        datePickerDialog.setMessage(getString(R.string.eventDatePickerMessage));
        datePickerDialog.show();

    }

    public void showTimePicker(View view) {

        int hour;
        int minute;

        if (mEventHour != -1 && mEventMinute != -1) {
            hour = mEventHour;
            minute = mEventMinute;
        }
        else {
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mEventHour = hourOfDay;
                mEventMinute = minute;
            }
        }, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.setMessage(getString(R.string.eventTimePickerMessage));
        timePickerDialog.show();
    }

    private View.OnClickListener mSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isValidInput()) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(mEventYear, mEventMonth, mEventDay, mEventHour, mEventMinute);
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        NewEventActivity.this.getString(R.string.dateFormatString));
                String dateString = dateFormat.format(calendar.getTime());
                if (mEvent == null) {
                    mEvent = new Event(mTitleEditText.getText().toString(),
                            dateString,
                            mLocationEditText.getText().toString(),
                            mCommentsEditText.getText().toString());

                    mEventDataSource.create(mEvent);
                }
                else {
                    mEvent.setTitle(mTitleEditText.getText().toString());
                    mEvent.setDate(dateString);
                    mEvent.setLocation(mLocationEditText.getText().toString());
                    mEvent.setComments(mCommentsEditText.getText().toString());
                    mEventDataSource.update(mEvent);
                }
                NewEventActivity.this.finish();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewEventActivity.this);
                builder.setMessage(getString(R.string.improperEventValuesAlertDialogMessage));
                builder.setPositiveButton(getString(R.string.invalidEventOkButton), null);
                builder.create().show();
            }
        }
    };

    private View.OnClickListener mCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
