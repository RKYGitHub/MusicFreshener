package com.rky.musicfreshener;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.rky.musicfreshener.MusicContract.MusicEntry;

/**
 * Helper for creation and various other operations for the DB.
 */
public class MusicDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Music.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MusicEntry.TABLE_NAME + " (" +
                    MusicEntry._ID + " INTEGER PRIMARY KEY," +
                    MusicEntry.COLUMN_NAME_GENRE + TEXT_TYPE + COMMA_SEP +
                    MusicEntry.COLUMN_NAME_ARTIST + TEXT_TYPE + COMMA_SEP +
                    MusicEntry.COLUMN_NAME_ALBUM + TEXT_TYPE + COMMA_SEP +
                    MusicEntry.COLUMN_NAME_RATING + TEXT_TYPE + COMMA_SEP +
                    MusicEntry.COLUMN_NAME_DATE + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MusicEntry.TABLE_NAME;

    public MusicDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // don't think this would ever happen, but it wouldn't make sense to delete old entries
        onCreate(db);
    }
}
