package edu.unc.dominno.litcommitapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class TodoTab extends ActionBarActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter adapter;
    ImageButton FAB;
    private String due_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_tab);

        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Popup window
                Intent i = new Intent(TodoTab.this, DateChooser.class);
                startActivityForResult(i, 2);
            }
        });

        adapter = new CustomCursorAdapter(this, null, 0);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent i = new Intent(TodoTab.this, TodoEditor.class);
                Uri uri = Uri.parse(TodoCP.DB_URI + "/" + arg3);
                i.putExtra(TodoCP.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(i,0);
            }});

        getLoaderManager().initLoader(1, null, this);

        due_date = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {
            case R.id.action_delete_all:
                deleteAllNotes();
                break;
            case R.id.action_delete_checked:
                deleteAllChecked();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllChecked() {
        Cursor cursor = getContentResolver().query(TodoCP.DB_URI, DBOpener.ALL_COLUMNS, null, null, null);
        cursor.moveToFirst();
        String where_clause;
        while (!cursor.isAfterLast()) {
            String checked = cursor.getString(cursor.getColumnIndex(DBOpener.NOTE_READ));
            cursor.getPosition();
            if (checked.equals("true")) {
               where_clause = DBOpener.TODO_ID + "=" + cursor.getInt(cursor.getColumnIndex(DBOpener.TODO_ID));
                getContentResolver().delete(TodoCP.DB_URI, where_clause, null);
            }
            cursor.moveToNext();
        }
        restartLoader();
    }

    private void deleteAllNotes() {
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            //Insert Data management code here
                            getContentResolver().delete(TodoCP.DB_URI, null, null);
                            restartLoader();
                            Toast.makeText(TodoTab.this,
                                    getString(R.string.all_deleted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }

    private Loader<Cursor> restartLoader() {
        return getLoaderManager().restartLoader(1, null, this);
    }

    private void insertNote(String text, String checked) {
        ContentValues values = new ContentValues();
        values.put(DBOpener.TODO_TEXT, text);
        values.put(DBOpener.NOTE_READ, checked);
        Uri noteUri = getContentResolver().insert(TodoCP.DB_URI, values);
        Log.d("MainActivity", "inserted note"      + noteUri.getLastPathSegment());
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new android.content.CursorLoader(this, TodoCP.DB_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void addNotePressed() {
        Intent add = new Intent(this, TodoEditor.class);
        add.putExtra("due date", due_date);
        startActivityForResult(add, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            restartLoader();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri u = data.getData();
            //Log.v("DOMINNO",u.toString());
            due_date = u.toString();
            Log.v("DOMINNO", "onActivityResult: " + due_date);
            addNotePressed();
        }
    }
}
