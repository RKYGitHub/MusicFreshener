package com.rky.musicfreshener;

import android.content.Context;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by RKY on 11/3/13.
 *
 * A set of static utility methods to deal with reading and writing data to/from a CSV
 */
public class CsvUtilities {
    /**
     * Provides an Adapter for the music CSV. Reads all lines in.
     * @param context current context
     * @return the adapter
     */
    static MusicAdapter getAdapter(Context context) {
        // TODO use external file location or SQLite DB, as resources are unable to be written
        CSVReader reader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.music_big)));
        List allLines = null;
        try {
            allLines = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MusicAdapter(context, R.id.textView, allLines);
    }
}
