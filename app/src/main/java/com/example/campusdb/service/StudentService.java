package com.example.campusdb.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.campusdb.model.Student;
import com.example.campusdb.util.CampusDbHelper;

public class StudentService {

    private static final String TABLE_NAME = "student";
    private static final String KEY_ID = "id";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String[] COLUMNS = {
            KEY_ID, KEY_LAST_NAME, KEY_FIRST_NAME};

    private final CampusDbHelper helper;

    public StudentService(Context context) {
        this.helper = new CampusDbHelper(context);
    }

    public void create(Student s) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LAST_NAME, s.getLastName());
        values.put(KEY_FIRST_NAME, s.getFirstName());
        db.insert(TABLE_NAME, null, values);
        Log.d("CampusDB insert", s.getLastName() + " " + s.getFirstName());
        db.close();
    }

    public void update(Student s) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, s.getId());
        values.put(KEY_LAST_NAME, s.getLastName());
        values.put(KEY_FIRST_NAME, s.getFirstName());
        db.update(TABLE_NAME, values, "id = ?",
                new String[]{s.getId() + ""});
        db.close();
    }

    public Student findById(int id) {
        Student s = null;
        SQLiteDatabase db = this.helper.getReadableDatabase();

        Cursor c = db.query(
                TABLE_NAME,
                COLUMNS,
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );

        if (c.moveToFirst()) {
            s = new Student();
            s.setId(c.getInt(0));
            s.setLastName(c.getString(1));
            s.setFirstName(c.getString(2));
        }

        c.close();
        db.close();
        return s;
    }

    public void delete(Student s) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?",
                new String[]{String.valueOf(s.getId())});
        Log.d("CampusDB delete", "Supprimé id=" + s.getId());
        db.close();
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;

        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Student s = new Student();
                s.setId(c.getInt(0));
                s.setLastName(c.getString(1));
                s.setFirstName(c.getString(2));
                students.add(s);
                Log.d("CampusDB findAll",
                        "id=" + s.getId()
                                + " " + s.getLastName()
                                + " " + s.getFirstName());
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return students;
    }
}