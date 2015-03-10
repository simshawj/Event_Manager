package com.jamessimshaw.eventmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jamessimshaw.eventmanager.R;
import com.jamessimshaw.eventmanager.models.Event;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {
    private ArrayList<Event> mEvents;
    private Context mContext;

    public EventAdapter (Context context, ArrayList<Event> events) {
        super();
        mEvents = events;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Event getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_layout, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
        TextView locationTextView = (TextView) convertView.findViewById(R.id.locationTextView);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);

        Event event = getItem(position);

        titleTextView.setText(event.getTitle());
        locationTextView.setText(event.getLocation());
        dateTextView.setText(event.getDate());

        return convertView;
    }
}
