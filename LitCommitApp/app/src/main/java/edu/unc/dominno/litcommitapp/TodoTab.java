package edu.unc.dominno.litcommitapp;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TodoTab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_tab);
        insertNote("test");

        Cursor cursor = getContentResolver().query(TodoCP.CONTENT_URI, DBOpener.ALL_COLUMNS, null, null, null, null);
        String[] notes = {DBOpener.NOTE_TEXT};
        int[] view = {android.R.id.text1};
        CursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, notes, view, 1);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);

    }

    private void insertNote(String text) {
        ContentValues values = new ContentValues();
        values.put(DBOpener.NOTE_TEXT, text);
        Uri noteUri = getContentResolver().insert(TodoCP.CONTENT_URI, values);
        Log.d("MainActivity", "inserted note" + noteUri.getLastPathSegment());
    }


}
