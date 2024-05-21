package com.example.bth4;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "school.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CLASS = "class";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CLASS_CODE = "class_code";
    public static final String COLUMN_CLASS_NAME = "class_name";
    public static final String COLUMN_CLASS_SIZE = "class_size";


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CLASS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CLASS_CODE + " TEXT, " +
                    COLUMN_CLASS_NAME + " TEXT, " +
                    COLUMN_CLASS_SIZE + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
