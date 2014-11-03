package com.rky.musicfreshener;

import android.provider.BaseColumns;

/**
 * Describes the music DB
 */
public final class MusicContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MusicContract() {}

    /* Inner class that defines the table contents */
    public static abstract class MusicEntry implements BaseColumns {
        public static final String TABLE_NAME = "Music";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_ARTIST = "artist";
        public static final String COLUMN_NAME_ALBUM = "album";
        public static final String COLUMN_NAME_GENRE = "genre";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
