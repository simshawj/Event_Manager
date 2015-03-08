package com.jamessimshaw.eventmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by James on 3/5/2015.
 */
public class NewEventActivity extends Activity implements TimePickerDialog.OnTimeSetListener {
    private EditText mTitleEditText;
    private EditText mDateEditText;
    private EditText mLocationEditText;
    private EditText mCommentsEditText;
    private Button mSaveButton;
    private Button mCancelButton;
    private int mEventHour;
    private int mEventMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        mEventHour = -1;
        mEventMinute = -1;


        mTitleEditText = (EditText) findViewById(R.id.eventTitleEditText);
        //mDateEditText = (EditText) findViewById(R.id.eventDateEditText);
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
        if (mEventMinute < 0 || mEventHour < 0)
            return false;

        return true;
    }

    public void showDatePicker(View view) {

    }

    public void showTimePicker(View view) {
        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mEventHour = hourOfDay;
        mEventMinute = minute;
    }

    private View.OnClickListener mSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isValidInput()) {
                Event event = new Event();
                event.setTitle(mTitleEditText.getText().toString());
                event.setLocation(mLocationEditText.getText().toString());

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

    public class TimePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), NewEventActivity.this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }
}
