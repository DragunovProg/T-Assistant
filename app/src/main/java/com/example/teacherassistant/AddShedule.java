package com.example.teacherassistant;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddShedule extends AppCompatActivity {

    Spinner subjectsSpin;
    Spinner groupsSpin;
    EditText time;
    EditText notes;
    DatePicker dateLesson;
    Button saveSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shedule);
        saveSchedule = (Button) findViewById(R.id.save_schedule);
        subjectsSpin = (Spinner)findViewById(R.id.spinnerSubjects);
        groupsSpin = (Spinner)findViewById(R.id.spinnerGroups);
        dateLesson = (DatePicker)findViewById(R.id.dateSchedule);

        ArrayList<String> subjectList = new ArrayList<>();
        ArrayList<String> groupList = new ArrayList<>();
        String qu = "SELECT * FROM subjects";
        Cursor cursor = MainActivity.database.execQuery(qu);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(getBaseContext(), "Нет записей", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                subjectList.add(cursor.getString(0));
                groupList.add(cursor.getString(1));
                cursor.moveToNext();
            }

            ArrayAdapter adapterSub = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subjectList);
            ArrayAdapter adapterGrp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, groupList);

            subjectsSpin.setAdapter(adapterSub);
            groupsSpin.setAdapter(adapterGrp);
            saveSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveSchedule();
                }
            });

        }
    }


        private void saveSchedule()  {


            time = (EditText) findViewById(R.id.timeText);
            notes = (EditText) findViewById(R.id.noteText);

            String times = time.getText().toString();
            String note = notes.getText().toString();
            String date = dateLesson.getYear() + "." + (dateLesson.getMonth() + 1) + "." + dateLesson.getDayOfMonth();
            if (note.length() < 5) {
                Toast.makeText(getBaseContext(), "Введите корректные данные", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = 0;

            String qu = "SELECT _id FROM schedules";
            Cursor cursor = MainActivity.database.execQuery(qu);
            if (cursor == null || cursor.getCount() == 0) {
                Toast.makeText(getBaseContext(), "Нет записей", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    id = Integer.parseInt(cursor.getString(0));

                    cursor.moveToNext();
                }
            }

            String sql = "INSERT INTO schedules VALUES('" + (id + 1) + "','"  + date + "'," +
                    "'" + note + "'," + "'" + subjectsSpin.getSelectedItem().toString() + "','" + groupsSpin.getSelectedItem().toString() + "','" +
                    times + "');";
            Log.d("Subjects", sql);
            if (MainActivity.database.execAction(sql)) {
                Toast.makeText(getBaseContext(), "Данные записаны", Toast.LENGTH_LONG).show();
                this.finish();
            } else
                Toast.makeText(getBaseContext(), "Ошибка записи", Toast.LENGTH_LONG).show();

        }


    }

