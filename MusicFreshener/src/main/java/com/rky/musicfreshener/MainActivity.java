package com.rky.musicfreshener;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Adds a new entry into the DB for the text present when the button was clicked.
     * @param button the add button that was clicked to get here
     */
    public void addMusic (View button) {
        Context context = button.getContext();

        // get info from fields
        String genre = getTextFromEditText(R.id.addMusicGenreInput);
        String artist = getTextFromEditText(R.id.addMusicArtistInput);
        String album = getTextFromEditText(R.id.addMusicAlbumInput);
        String rating = getTextFromEditText(R.id.addMusicRatingInput);
        String date = getTextFromEditText(R.id.addMusicDateInput);
        DbUtilities.insertMusic(context, genre, artist, album, rating, date);

        // display a toast informing the user it was added
        Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show();
    }

    public String getTextFromEditText(int fieldId) {
        return ((EditText) findViewById(fieldId)).getText().toString();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                final Bundle savedInstanceState) {
            final int sectionNum = getArguments().getInt(ARG_SECTION_NUMBER);
            // create main list of music rows
            View view = inflater.inflate(R.layout.fragment_main, container, false);

            //setup DB
            MusicDbHelper dbHelper = new MusicDbHelper(view.getContext());
            final SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
            final SimpleCursorAdapter adapter = DbUtilities.getInitialAdapter(view.getContext());

            // set list of rows
            ListView list = (ListView) view.findViewById(R.id.listView);
            list.setAdapter(adapter);
            list.setLongClickable(true);
            list.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Context context = view.getContext();
                    // get info from fields
                    String genre = ((TextView) view.findViewById(R.id.rowGenre)).getText().toString();
                    String artist = ((TextView) view.findViewById(R.id.rowArtist)).getText().toString();
                    String album = ((TextView) view.findViewById(R.id.rowAlbum)).getText().toString();
                    String rating = ((TextView) view.findViewById(R.id.rowRating)).getText().toString();
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    DbUtilities.insertMusic(context, genre, artist, album, rating, date);

                    // display a toast informing the user it was added
                    Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show();

                    // update view
                    switch (sectionNum) {
                        case 1:// listen to today
                            adapter.swapCursor(DbUtilities.getListenNowCursor(dbReader));
                            break;
                        case 3: // history
                            adapter.swapCursor(DbUtilities.getHistoryCursor(dbReader));
                            break;
                        default:
                            view = inflater.inflate(R.layout.whoops, container, false);
                            break;
                    }

                    return false;
                }
            });

            // set view per section
            switch (sectionNum) {
                case 1:// listen to today
                    adapter.swapCursor(DbUtilities.getListenNowCursor(dbReader));
                    break;
                case 2: // add new
                    view = inflater.inflate(R.layout.fragment_add_new, container, false);

                    // set default date as today
                    EditText dateField = (EditText) view.findViewById(R.id.addMusicDateInput);
                    dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    break;
                case 3: // history
                    adapter.swapCursor(DbUtilities.getHistoryCursor(dbReader));
                    break;
                default:
                    view = inflater.inflate(R.layout.whoops, container, false);
                    break;
            }

            return view;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
