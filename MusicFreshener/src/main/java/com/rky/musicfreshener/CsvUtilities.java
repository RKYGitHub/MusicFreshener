package com.rky.musicfreshener;

import android.content.Context;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by RKY on 11/3/13.
 */
public class CsvUtilities {
/*
    static CursorAdapter getAdapter(Context context) {
        MatrixCursor matrix = new MatrixCursor(new String[]{"col1", "col2", "col3", "col4", "col5", "col6"});
        matrix.addRow(new String[]{"val11", "val12", "val13", "val14", "val15", "val16"});
        matrix.addRow(new String[]{"val21", "val22", "val23", "val24", "val25", "val26"});


    }
*/
    static MusicAdapter getAdapter(Context context) {
        CSVReader reader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.music)));
        List allLines = null;
        try {
            allLines = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MusicAdapter(context, R.id.textView, allLines);
    }

}
