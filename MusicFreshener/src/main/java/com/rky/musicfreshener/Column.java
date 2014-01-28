package com.rky.musicfreshener;

/**
 * Created by RKY on 11/3/13.
 *
 * This is the ordered list of columns in the csv.
 */
public enum Column {
    GENRE(0),
    ARTIST(1),
    ALBUM(2),
    RATING(3),
    DATE(4),
    NOTES(5);

    private final int index;

    private Column(int value) {
        this.index = value;
    }

    public int getIndex() {
        return index;
    }
}
