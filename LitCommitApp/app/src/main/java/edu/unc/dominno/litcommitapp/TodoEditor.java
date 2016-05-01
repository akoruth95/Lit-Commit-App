package edu.unc.dominno.litcommitapp;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import static edu.unc.dominno.litcommitapp.R.*;

public class TodoEditor extends AppCompatActivity {

    private String action;
    private EditText text;
    private String initial;
    private String where_clause;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_todo_editor);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        text = (EditText) findViewById(id.editor);
        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(TodoCP.CONTENT_ITEM_TYPE);
        if (uri == null) {
            action = Intent.ACTION_INSERT;
            setTitle("New Todo");
        } else {
            action = Intent.ACTION_EDIT;
            where_clause = DBOpener.TODO_ID + "=" + uri.getLastPathSegment();
            Cursor cursor = getContentResolver().query(uri, DBOpener.ALL_COLUMNS, where_clause, null, null);
            cursor.moveToFirst();//move to first row
            initial = (cursor.getString(cursor.getColumnIndex(DBOpener.TODO_TEXT)));
            String read = (cursor.getString(cursor.getColumnIndex(DBOpener.NOTE_READ)));
            ImageButton button = (ImageButton) findViewById(id.done);
            if (read.equals("true")) {
                button.setBackgroundResource(drawable.green_check);
            } else if (read.equals("false")) {
                button.setBackgroundResource(drawable.red_check);
            }
            text.setText(initial);
            text.requestFocus();
        }
    }

    private void finishTodo() {
        String text = this.text.getText().toString().trim();
        if (action == Intent.ACTION_INSERT) {
            if (text.length() == 0) {
                setResult(RESULT_CANCELED);
            } else {
                insertNote(text, "false");
                setResult(RESULT_OK);
            }
            finish();
        } else if (action == Intent.ACTION_EDIT) {
            if (text.length() == 0) {
                setResult(RESULT_OK);
                finish();
            } else if (text.equals(initial)) {
                setResult(RESULT_OK);
                finish();
            } else {
                updateNote(text);
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void updateNote(String text) {
        ContentValues values = new ContentValues();
        values.put(DBOpener.TODO_TEXT, text);
        getContentResolver().update(TodoCP.DB_URI, values, where_clause, null);
        setResult(RESULT_OK);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void swap(View view) {
        if (view.getTag()== "false") {
            view.setTag("true");
            view.setBackgroundResource(drawable.green_check);
            updateReadStatus("true");
        } else {
            view.setTag("false");
            view.setBackgroundResource(drawable.red_check);
            updateReadStatus("false");
        }
    }

    private void updateReadStatus(String text) {
        ContentValues values = new ContentValues();
        values.put(DBOpener.NOTE_READ, text);
        getContentResolver().update(TodoCP.DB_URI, values, where_clause, null);
    }

    private void insertNote(String text, String read) {
        ContentValues values = new ContentValues();
        values.put(DBOpener.TODO_TEXT, text);
        values.put(DBOpener.NOTE_READ, read);
         getContentResolver().insert(TodoCP.DB_URI, values);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishTodo();
    }


}
