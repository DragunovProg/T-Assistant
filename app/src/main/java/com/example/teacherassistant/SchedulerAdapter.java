package com.example.teacherassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SchedulerAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<String> subjects;
    ArrayList<String> groups;
    ArrayList<Integer> hours;
    ArrayList<String> dates;
    ArrayList<String> notes;

    public SchedulerAdapter(Activity activity, ArrayList<String> subjects, ArrayList<String> groups, ArrayList<String> dates,
                            ArrayList<String> notes, ArrayList<Integer> hours) {

        this.subjects = subjects;
        this.groups = groups;
        this.dates = dates;
        this.hours = hours;
        this.notes = notes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.template_schedule, null);
        }
        TextView infoSchedule = (TextView)v.findViewById(R.id.textSchedule);
//        Button btnChange = (Button)v.findViewById(R.id.button_change);
 //       btnChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent launchChange = new Intent(ScheduleActivity.this, ChangerSchedule.class);
//            }
//        });

        return null;
    }
}
