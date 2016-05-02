package edu.unc.dominno.litcommitapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by koruth on 4/28/2016.
 */
public class DBOpener extends SQLiteOpenHelper{

    //Constants for db name and version
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_NOTES = "notes";
    public static final String TODO_ID = "_id";
    public static final String TODO_TEXT = "noteText";
    public static final String TODO_CREATED = "noteCreated";
    public static final String NOTE_READ = "noteRead";
    public static final String DUE_DATE = "dueDate";


    public static final String[] ALL_COLUMNS = {TODO_ID, TODO_TEXT, TODO_CREATED, NOTE_READ, DUE_DATE};

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TODO_TEXT + " TEXT, " +
                    TODO_CREATED + " TEXT default CURRENT_TIMESTAMP," + NOTE_READ + " TEXT, " +
                    DUE_DATE + " TEXT" +
                    ")";


    public DBOpener(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NOTES);
        onCreate(db);
    }
}
