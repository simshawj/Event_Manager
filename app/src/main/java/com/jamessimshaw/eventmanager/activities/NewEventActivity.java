package com.jamessimshaw.eventmanager.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.jamessimshaw.eventmanager.models.Event;
import com.jamessimshaw.eventmanager.datasources.EventDataSource;
import com.jamessimshaw.eventmanager.R;

import java.util.Calendar;

/**
 * Created by James on 3/5/2015.
 */
public class NewEventActivity extends Activity{
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
    private EventDataSource mEventDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mEventHour = -1;
        mEventMinute = -1;
        mEventDay = -1;
        mEventMonth = -1;
        mEventYear = -1;

        mEventDataSource = new EventDataSource(this);

        mTitleEditText = (EditText) findViewById(R.id.eventTitleEditText);
        mLocationEditText = (EditText) findViewById(R.id.eventLocationEditText);
        mCommentsEditText = (EditText) findViewById(R.id.eventCommentsEditText);
        mSaveButton = (Button) findViewById(R.id.eventSaveButton);
        mCancelButton = (Button) findViewById(R.id.eventCancelButton);

        mSaveButton.setOnClickListener(mSaveClickListener);
        mCancelButton.setOnClickListener(mCancelClickListener);
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
        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mEventDay = dayOfMonth;
                mEventMonth = monthOfYear;
                mEventYear = year;
            }
        }, curYear, curMonth, curDay);
        datePickerDialog.setMessage(getString(R.string.eventDatePickerMessage));
        datePickerDialog.show();

    }

    public void showTimePicker(View view) {
        final Calendar calendar = Calendar.getInstance();

        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mEventHour = hourOfDay;
                mEventMinute = minute;
            }
        }, curHour, curMinute, DateFormat.is24HourFormat(this));
        timePickerDialog.setMessage(getString(R.string.eventTimePickerMessage));
        timePickerDialog.show();
    }

    private View.OnClickListener mSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isValidInput()) {
                String dateString = String.format("%04d", mEventYear) + "-" +
                        String.format("%02d", mEventMonth) + "-" +
                        String.format("%02d", mEventDay) + " " +
                        String.format("%02d", mEventHour) + ":" +
                        String.format("%02d", mEventMinute);
                Event event = new Event(mTitleEditText.getText().toString(),
                        dateString,
                        mLocationEditText.getText().toString(),
                        mCommentsEditText.getText().toString());
                mEventDataSource.create(event);
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
