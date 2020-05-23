package com.example.teacherassistant;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditSubjects extends AppCompatActivity {
    Activity activity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_subject);
        Button loadButton = (Button) findViewById(R.id.buttonLoad);
        assert loadButton != null;
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sub = (EditText) findViewById(R.id.subject_edit);
                String qu = "SELECT * FROM subjects WHERE subject = '" + sub.getText().toString() + "'";
                Cursor cr = MainActivity.database.execQuery(qu);
                if (cr == null || cr.getCount() == 0) {
                    Toast.makeText(getBaseContext(), "Предмет не найден", Toast.LENGTH_LONG).show();
                } else {
                    cr.moveToFirst();
                    try {
                        EditText group = (EditText) findViewById(R.id.group_edit);
                        assert group != null;
                        group.setText(cr.getString(1));

                    } catch (Exception e) {
                    }
                }
            }
        });


        Button saveEdit = (Button) findViewById(R.id.buttonSave);
        assert saveEdit != null;
        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sub = (EditText) findViewById(R.id.subject_edit);
                EditText group = (EditText) findViewById(R.id.group_edit);

                String qu = "UPDATE subjects SET subject = '" + sub.getText().toString() + "' , " +
                        " st_group = '" + group.getText().toString() + "' " + "WHERE subject = '" + sub.getText().toString() + "'";
                Log.d("EditStudentActivity", qu);
                if (MainActivity.database.execAction(qu)) {
                    Toast.makeText(getBaseContext(), "Изменения сохранены", Toast.LENGTH_LONG).show();
                    activity.finish();

                } else
                    Toast.makeText(getBaseContext(), "Ошибка при сохранении данных", Toast.LENGTH_LONG).show();

            }
        });
    }
}
