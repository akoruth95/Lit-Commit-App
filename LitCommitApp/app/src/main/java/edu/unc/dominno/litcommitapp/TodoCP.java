package edu.unc.dominno.litcommitapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by koruth on 4/28/2016.
 */
public class TodoCP extends ContentProvider {

    private static final String AUTHORITY = "edu.unc.dominno.litcommitapp.todocp";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int NOTES = 1;//id to return data
    private static final int NOTES_ID = 2;//id to return a single record

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, NOTES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTES_ID);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DBOpener opener = new DBOpener(getContext());

        db = opener.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return db.query(DBOpener.TABLE_NOTES, DBOpener.ALL_COLUMNS, selection, null, null, null, DBOpener.NOTE_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = db.insert(DBOpener.TABLE_NOTES, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return db.delete(DBOpener.TABLE_NOTES, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return db.update(DBOpener.TABLE_NOTES, values, selection, selectionArgs);
    }
}
