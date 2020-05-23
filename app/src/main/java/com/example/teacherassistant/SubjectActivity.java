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

public class SubjectActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    FloatingActionButton btnAddSubject;
    ListView listSubjects;
    ArrayList<String> subjects;
    ArrayList<String> groups;
    ArrayList<String> subjectsInfo;
    Activity activity = this;
    ArrayAdapter subjectAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject);
        openSubjectAdder();
        subjects = new ArrayList<>();
        groups = new ArrayList<>();
        subjectsInfo = new ArrayList<>();
        listSubjects = (ListView)findViewById(R.id.listSubjects);
        loadSubjects();
        listSubjects.setOnItemLongClickListener(this);

    }

    private void loadSubjects() {
        subjectsInfo.clear();
        subjects.clear();
        groups.clear();
        String qu = "SELECT * FROM subjects ORDER BY subject";
        Cursor cursor = MainActivity.database.execQuery(qu);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(getBaseContext(), "В списке нет предметов", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                subjects.add(cursor.getString(0));
                subjectsInfo.add("Предмет : " + cursor.getString(0) + "\nГруппа : " + cursor.getString(1));

                groups.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, subjectsInfo);
        listSubjects.setAdapter(adapter);

    }

    private void openSubjectAdder() {
        btnAddSubject = (FloatingActionButton)findViewById(R.id.addSubjectButton);
        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchSubjectAdder  = new Intent(activity, addSubjectActivity.class);
                startActivity(launchSubjectAdder);
            }
        });

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Удалить предмет");
        alert.setMessage("Вы действительно хотите удалить предмет из списка?");

        alert.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String qu = "DELETE FROM subjects WHERE subject = '" + subjects.get(position) + "' AND st_group = '" + groups.get(position) + "'";
                if (MainActivity.database.execAction(qu)) {
                    Toast.makeText(getBaseContext(), "Удалено", Toast.LENGTH_LONG).show();
                    loadSubjects();
                } else {
                    Toast.makeText(getBaseContext(), "Ошибка", Toast.LENGTH_LONG).show();
                    loadSubjects();
                }
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.subject_menu, menu);
        return true;
    }

    public void refreshSubjects(MenuItem item) {
        loadSubjects();
    }

    public void editSubjects(MenuItem item) {
        Intent launchEdit = new Intent(getBaseContext(), EditSubjects.class);
        startActivity(launchEdit);

    }

}
