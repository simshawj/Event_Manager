package com.jamessimshaw.eventmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by James on 3/5/2015.
 */
public class NewEventActivity extends Activity {
    EditText mTitleEditText;
    EditText mDateEditText;
    EditText mLocationEditText;
    EditText mCommentsEditText;
    Button mSaveButton;
    Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mTitleEditText = (EditText) findViewById(R.id.eventTitleEditText);
        mDateEditText = (EditText) findViewById(R.id.eventDateEditText);
        mLocationEditText = (EditText) findViewById(R.id.eventLocationEditText);
        mCommentsEditText = (EditText) findViewById(R.id.eventCommentsEditText);
        mSaveButton = (Button) findViewById(R.id.eventSaveButton);
        mCancelButton = (Button) findViewById(R.id.eventCancelButton);

        mSaveButton.setOnClickListener(mSaveClickListener);
        mCancelButton.setOnClickListener(mCancelClickListener);
    }

    private View.OnClickListener mSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
