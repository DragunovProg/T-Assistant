package com.example.teacherassistant;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teacherassistant.database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static DBHelper database;
    ImageButton schedule;
    ImageButton subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DBHelper(this);
        openSchedule();
        openSubject();


    }

    public void openSchedule() {


        schedule = (ImageButton)findViewById(R.id.btn_schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchSchedule = new Intent(getBaseContext(), ScheduleActivity.class);
                startActivity(launchSchedule);
            }
        });

    }

    public void openSubject() {
        schedule = (ImageButton) findViewById(R.id.btn_subject);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchSchedule = new Intent(getBaseContext(), SubjectActivity.class);
                startActivity(launchSchedule);
            }
        });
    }

}
