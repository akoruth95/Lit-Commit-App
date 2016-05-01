package edu.unc.dominno.litcommitapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by koruth on 4/29/2016.
 */
public class CustomCursorAdapter extends CursorAdapter{

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String noteText = cursor.getString(cursor.getColumnIndex(DBOpener.TODO_TEXT));
        String read = cursor.getString(cursor.getColumnIndex(DBOpener.NOTE_READ));

        int newline = noteText.indexOf(10);
        if (newline > 0) {
            noteText = noteText.substring(0, newline);
        }
        TextView note = (TextView) view.findViewById(R.id.note);
        note.setText(noteText);

        if (read.equals("false")) {
            note.setBackgroundResource(R.color.light_red);
        } else if (read.equals("true")) {
            note.setBackgroundResource(R.color.light_green);
        }

    }
}
