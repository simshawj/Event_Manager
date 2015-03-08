package com.jamessimshaw.eventmanager;

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

import java.util.Calendar;
import java.util.Date;

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
    private int mDay;
    private int mMonth;
    private int mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mEventHour = -1;
        mEventMinute = -1;
        mDay = -1;
        mMonth = -1;
        mYear = -1;

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
        if (mEventMinute < 0 || mEventHour < 0 || mDay < 0 || mMonth < 0 || mYear < 0)
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
                mDay = dayOfMonth;
                mMonth = monthOfYear;
                mYear = year;
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
                Event event = new Event();
                event.setTitle(mTitleEditText.getText().toString());
                event.setLocation(mLocationEditText.getText().toString());
                event.setComments(mCommentsEditText.getText().toString());

                

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
