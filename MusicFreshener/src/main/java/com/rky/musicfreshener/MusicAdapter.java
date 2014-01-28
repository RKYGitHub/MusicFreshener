package com.rky.musicfreshener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RKY on 1/28/14.
 */
public class MusicAdapter extends ArrayAdapter {
    public MusicAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.music_row, parent, false);

        TextView album = (TextView) row.findViewById(R.id.album);
        TextView artist = (TextView) row.findViewById(R.id.artist);
        TextView genre = (TextView) row.findViewById(R.id.genre);
        TextView rating = (TextView) row.findViewById(R.id.rating);
        TextView dateLastListened = (TextView) row.findViewById(R.id.dateLastListened);

        String[] currentRowArray = (String[]) this.getItem(position);

        genre.setText(currentRowArray[Column.GENRE.getIndex()]);
        artist.setText(currentRowArray[Column.ARTIST.getIndex()]);
        album.setText(currentRowArray[Column.ALBUM.getIndex()]);
        rating.setText(currentRowArray[Column.RATING.getIndex()]);
        dateLastListened.setText(currentRowArray[Column.DATE.getIndex()]);

        return row;
    }
}
