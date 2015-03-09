package com.jamessimshaw.eventmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private String[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mEventsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerItems = getResources().getStringArray(R.array.drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mainActivityView);
        mDrawerList = (ListView) findViewById(R.id.eventDrawer);
        mEventsListView = (ListView) findViewById(R.id.eventListView);

        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mDrawerItems));
        //mDrawerList.setOnClickListener(null);

        displayEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Intent intent = new Intent(this, NewEventActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayEvents() {
        EventDataSource eventDataSource = new EventDataSource(this);
        ArrayList<Event> events = eventDataSource.read(EventDataSource.ALL_FUTURE);

        mEventsListView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, events));  //TODO: Create custom adapter
    }
}
