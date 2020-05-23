package com.example.teacherassistant;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addSubjectActivity extends AppCompatActivity {

    EditText subjects;
    EditText groups;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subject);
        btnSave = (Button)findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSubject(v);
            }
        });
    }

    private void saveSubject(View v) {
        subjects = (EditText)findViewById(R.id.Subject);
        groups = (EditText)findViewById(R.id.Group);
        String subject = subjects.getText().toString();
        String group = groups.getText().toString();
        if (subject.length() < 5 & group.length() < 4) {
            Toast.makeText(getBaseContext(), "Введите корректные данные", Toast.LENGTH_SHORT).show();
            return;
        }

        String sql = "INSERT INTO subjects VALUES('" + subject + "'," +
                "'" + group + "');";
        Log.d("Subjects", sql);
        if (MainActivity.database.execAction(sql)) {
            Toast.makeText(getBaseContext(), "Данные записаны", Toast.LENGTH_LONG).show();
            this.finish();
        } else
            Toast.makeText(getBaseContext(), "Ошибка записи", Toast.LENGTH_LONG).show();

    }
}
