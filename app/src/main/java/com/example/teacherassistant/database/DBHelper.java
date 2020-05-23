package com.example.teacherassistant.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper{

    Activity activity;
    private SQLiteDatabase db;

    public DBHelper(Activity activity) {
        this.activity = activity;
        db = activity.openOrCreateDatabase("Schedule", activity.MODE_PRIVATE, null);
        createTable();
    }

    public void createTable() {
        try {

        db.execSQL("CREATE TABLE IF NOT EXISTS subjects (subject varchar(25) primary key,st_group varchar(25))");
        db.execSQL("CREATE TABLE IF NOT EXISTS schedules (_id integer primary key autoincrement not null ,date_subject varchar(10),notes text," +
                "subject varchar(25), st_group varchar(25),hour integer,FOREIGN KEY(subject) REFERENCES subjects(subject),FOREIGN KEY(st_group) REFERENCES subjects(st_group))");
        } catch (Exception e) {
            Toast.makeText(activity, "Error Occured for create table", Toast.LENGTH_LONG).show();
        }
    }


    public boolean execAction(String qu) {
        Log.i("DBHelper", qu);
        try {
            db.execSQL(qu);
        } catch (Exception e) {
            Log.e("DBHelper", qu);
            Toast.makeText(activity, "Error Occured for execAction", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public Cursor execQuery(String qu) {
        try {
            return db.rawQuery(qu, null);
        } catch (Exception e) {
            Log.e("DBHelper", qu);
            Toast.makeText(activity,"Error Occurred for execAction",Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
