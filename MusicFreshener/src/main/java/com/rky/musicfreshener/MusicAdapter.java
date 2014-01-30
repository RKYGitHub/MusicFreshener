package com.rky.musicfreshener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static com.rky.musicfreshener.Column.*;

/**
 * Custom adapter so that the data from the CSV can be put into the list view.
 * Note: the data type is expected to always be an array of Strings (i.e. ArrayAdapter<String[]>)
 * The column indices are defined in the Column enum.
 */
public class MusicAdapter extends ArrayAdapter {
    public MusicAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    /**
     * A custom View for the data based on the music_row layout in this project to be put into a ListView.
     * @param position position of current item in the Array
     * @param convertView unused, but we're overriding and need to leave it
     * @param parent parent view
     * @return a row of music data, put into the music_row layout view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create main view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.music_row, parent, false);

        // get each of the sub-views
        TextView album = (TextView) row.findViewById(R.id.album);
        TextView artist = (TextView) row.findViewById(R.id.artist);
        TextView genre = (TextView) row.findViewById(R.id.genre);
        TextView rating = (TextView) row.findViewById(R.id.rating);
        TextView dateLastListened = (TextView) row.findViewById(R.id.dateLastListened);

        // set sub-views
        String[] currentRowArray = (String[]) this.getItem(position);
        genre.setText(currentRowArray[GENRE.getIndex()]);
        artist.setText(currentRowArray[ARTIST.getIndex()]);
        album.setText(currentRowArray[ALBUM.getIndex()]);
        rating.setText(currentRowArray[RATING.getIndex()]);
        dateLastListened.setText(currentRowArray[DATE.getIndex()]);

        return row;
    }
}
