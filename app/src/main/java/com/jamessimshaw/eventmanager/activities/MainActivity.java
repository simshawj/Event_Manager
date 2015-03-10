package com.jamessimshaw.eventmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jamessimshaw.eventmanager.adapters.EventAdapter;
import com.jamessimshaw.eventmanager.models.Event;
import com.jamessimshaw.eventmanager.datasources.EventDataSource;
import com.jamessimshaw.eventmanager.R;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mEventsListView;
    private int mDateWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] drawerItems = getResources().getStringArray(R.array.drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mainActivityView);
        mDrawerList = (ListView) findViewById(R.id.eventDrawer);
        mEventsListView = (ListView) findViewById(R.id.eventListView);

        mDateWindow = EventDataSource.TODAY;

        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, drawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawerOpened, R.string.drawerClosed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

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

        if (id == R.id.action_new) {
            Intent intent = new Intent(this, NewEventActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayEvents() {
        EventDataSource eventDataSource = new EventDataSource(this);
        final ArrayList<Event> events = eventDataSource.read(mDateWindow);

        mEventsListView.setAdapter(new EventAdapter(this, events));
        mEventsListView.setEmptyView(findViewById(R.id.emptyView));

        mEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
                intent.putExtra("event", events.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_new).setVisible(!mDrawerLayout.isDrawerOpen(mDrawerList));
        return super.onPrepareOptionsMenu(menu);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    mDateWindow = EventDataSource.TODAY;
                    break;
                case 1:
                    mDateWindow = EventDataSource.SEVEN_DAYS;
                    break;
                case 2:
                    mDateWindow = EventDataSource.THIRTY_DAYS;
                    break;
                case 3:
                    mDateWindow = EventDataSource.ALL_FUTURE;
                    break;
                default:
                    mDateWindow = EventDataSource.ALL_FUTURE;
            }
            displayEvents();
        }
    }
}
