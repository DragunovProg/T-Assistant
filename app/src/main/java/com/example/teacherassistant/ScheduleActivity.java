package com.example.teacherassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    FloatingActionButton btnAddSchedule;
    ListView listSchedule;
    ArrayList<String> subjects;
    ArrayList<String> dates;
    ArrayList<String> info;
    ArrayAdapter adapter;
    ArrayList<Integer> _id;
    Activity activity = this;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule);
        openAddSchedule();
        _id = new ArrayList<>();
        String qu = "SELECT _id FROM schedules";
        Cursor cursor = MainActivity.database.execQuery(qu);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(getBaseContext(), "Нет записей", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                _id.add(Integer.parseInt(cursor.getString(0)));

                cursor.moveToNext();
            }
        }

        subjects = new ArrayList<>();
        dates = new ArrayList<>();
        info = new ArrayList<>();
        listSchedule = (ListView) findViewById(R.id.list_schedule);
        loadSchedules();
        listSchedule.setOnItemLongClickListener(this);

    }

    private void loadSchedules() {
        info.clear();
        subjects.clear();
        dates.clear();
        String qu = "SELECT * FROM schedules ORDER BY subject";
        Cursor cursor = MainActivity.database.execQuery(qu);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(getBaseContext(), "В расписании нет занятий", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                subjects.add(cursor.getString(3));
                info.add("Дата занятия : " + cursor.getString(1) + "\nПредмет : " + cursor.getString(3) + "\nГруппа " + cursor.getString(4) + "\nДлительнось : " + cursor.getString(5) +
                " ч." + "\nТема : " + cursor.getString(2));

                dates.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, info);
        listSchedule.setAdapter(adapter);
    }

     public void openAddSchedule() {
        btnAddSchedule = (FloatingActionButton)findViewById(R.id.add_schedule);
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchAddSchedule = new Intent(getBaseContext(), AddShedule.class);
                startActivity(launchAddSchedule);
            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        final int idSchedule = _id.get(position);
        alert.setTitle("Удалить урок");
        alert.setMessage("Вы действительно хотите удалить урок из списка?");
        alert.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String qu = "DELETE FROM schedules WHERE _id = '" + idSchedule  + "'";

                if (MainActivity.database.execAction(qu)) {
                    loadSchedules();
                    Toast.makeText(getBaseContext(), "Удалено", Toast.LENGTH_LONG).show();
                } else {
                    loadSchedules();
                    Toast.makeText(getBaseContext(), "Ошибка", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Нет", null);
        alert.show();
        return true;
    }

    public void refreshSchedule(MenuItem item) {
        loadSchedules();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.schedule_menu, menu);
        return true;
    }




}
