package com.rky.musicfreshener;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import static com.rky.musicfreshener.MusicContract.MusicEntry.*;

import com.rky.musicfreshener.MusicContract.MusicEntry;

/**
 * Contains static helper methods for all DB-related work after creation.
 */
public class DbUtilities {
    /**
     * Inserts a row of music.
     * @param context the context of the app, used to setup the DB
     * @param genre genre
     * @param artist artist
     * @param album album
     * @param rating rating number
     * @param date date in format "yyyy-mm-dd"
     * @return row id
     */
    public static long insertMusic (Context context, String genre, String artist, String album, String rating, String date) {
        MusicDbHelper dbHelper = new MusicDbHelper(context);
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();

        // set the values
        ContentValues values = new ContentValues();
        values.put(MusicEntry.COLUMN_NAME_GENRE, genre);
        values.put(MusicEntry.COLUMN_NAME_ARTIST, artist);
        values.put(MusicEntry.COLUMN_NAME_ALBUM, album);
        values.put(MusicEntry.COLUMN_NAME_RATING, rating);
        values.put(MusicEntry.COLUMN_NAME_DATE, date);

        return dbWriter.insert(MusicEntry.TABLE_NAME, null, values);
    }

    /**
     * Gets the listen now cursor so the adapter can swap to this.
     * @param db the database to insert into
     * @return a cursor for the Listen Now screen
     */
    public static Cursor getListenNowCursor(SQLiteDatabase db) {
        int daysArtist = 7; // TODO make # days for artist a setting
        int daysAlbum = 30; // TODO make # days for album a setting
        daysArtist += 1; // adjusting to show correctly
        daysAlbum += 1; // adjusting to show correctly

        String table = TABLE_NAME + " m";
        String[] columns = {COLUMN_NAME_ID, COLUMN_NAME_GENRE, COLUMN_NAME_ARTIST, COLUMN_NAME_ALBUM,
            "max(" + COLUMN_NAME_RATING + ") as 'rating'",
            "max(" + COLUMN_NAME_DATE + ") as 'date'"};
        String groupingClause = COLUMN_NAME_ARTIST + ", " + COLUMN_NAME_ALBUM;
        String havingClause =
            COLUMN_NAME_ARTIST + " not in " +
                "(" +
                    "select distinct " + COLUMN_NAME_ARTIST +
                    " from " + TABLE_NAME + " mArtist" +
                    " where (julianday('now') - julianday(mArtist." + COLUMN_NAME_DATE + ")) < " + daysArtist +
                ")" +
            " and " + COLUMN_NAME_ALBUM + " not in " +
                "(" +
                    "select distinct " + COLUMN_NAME_ALBUM +
                    " from " + TABLE_NAME + " mAlbum" +
                    " where (julianday('now') - julianday(mAlbum." + COLUMN_NAME_DATE + ")) < " + daysAlbum +
                    " and mAlbum." + COLUMN_NAME_ARTIST + " = m." + COLUMN_NAME_ARTIST +
                ")";

        Cursor cursor = db.query(
                table,
                columns, // all columns
                null, // no WHERE
                null, // no args for selection
                groupingClause, // no grouping
                havingClause, // no having
                COLUMN_NAME_RATING + " DESC, date(" + COLUMN_NAME_DATE + ") ASC"
        );
        return cursor;
    }

    /**
     * Gets the history cursor so the adapter can swap to this.
     * @param db the database to insert into
     * @return a cursor for the History screen
     */
    public static Cursor getHistoryCursor(SQLiteDatabase db) {
        Cursor cursor = db.query(
                MusicEntry.TABLE_NAME,
                null, // all columns
                null, // all rows
                null, // no args for selection
                null, // no grouping
                null, // no filtering
                "julianday(" + COLUMN_NAME_DATE + ") DESC" // sort by date descending
        );
        return cursor;
    }

    /**
     * Gets the initial adapter with no cursor associated.
     * @param context context of the app
     * @return the adapter with no cursor
     */
    public static SimpleCursorAdapter getInitialAdapter (Context context) {
        String[] columns = {
                COLUMN_NAME_GENRE,
                COLUMN_NAME_ARTIST,
                COLUMN_NAME_ALBUM,
                COLUMN_NAME_RATING,
                COLUMN_NAME_DATE
        };
        int[] to = {
                R.id.rowGenre,
                R.id.rowArtist,
                R.id.rowAlbum,
                R.id.rowRating,
                R.id.rowDate
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter (
                context,
                R.layout.music_row,
                null,
                columns,
                to,
                0
        );

        return adapter;
    }
}
