package com.example.bth4;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText classCodeInput, classNameInput, classSizeInput;
    private DatabaseHelper dbHelper;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        classCodeInput = findViewById(R.id.classCodeInput);
        classNameInput = findViewById(R.id.classNameInput);
        classSizeInput = findViewById(R.id.classSizeInput);

        Button insertButton = findViewById(R.id.insertButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertClass();
            }
        });

        updateListView();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void insertClass() {
        String classCode = classCodeInput.getText().toString().trim();
        String className = classNameInput.getText().toString().trim();
        int classSize = Integer.parseInt(classSizeInput.getText().toString().trim());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CLASS_CODE, classCode);
        values.put(DatabaseHelper.COLUMN_CLASS_NAME, className);
        values.put(DatabaseHelper.COLUMN_CLASS_SIZE, classSize);

        long newRowId = db.insert(DatabaseHelper.TABLE_CLASS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Thêm lớp học thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lỗi khi thêm lớp học", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void updateListView() {
        ArrayList<String> classList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_CLASS,
                new String[]{DatabaseHelper.COLUMN_CLASS_CODE, DatabaseHelper.COLUMN_CLASS_NAME, DatabaseHelper.COLUMN_CLASS_SIZE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String classCode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CLASS_CODE));
            @SuppressLint("Range") String className = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CLASS_NAME));
            @SuppressLint("Range") int classSize = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CLASS_SIZE));
            classList.add(classCode + " - " + className + " - " + classSize);
        }
        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        ListView listView = findViewById(R.id.classListView);
        listView.setAdapter(adapter);
    }

}