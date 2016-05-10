package com.example.android.popularmovies_stage2;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies_stage2.DataObjects.Movie;

/*
*
* @brief: Main activity and entry point into the app
* It inflates the MovieListFragment for the main UI
*
* */
public class MainActivity extends ActionBarActivity implements MovieListFragment.Callback{

        private final String LOG_TAG = MainActivity.class.getSimpleName();
        private static final String DETAILFRAGMENT_TAG = "DFTAG";
        private FragmentManager fragmentManager = getFragmentManager();
        private boolean mTwoPane;

    /**
     * @brief on create event
     * @param savedInstanceState
     */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            if (findViewById(R.id.movie_detail_container) != null) {

                mTwoPane = true;

                if (savedInstanceState == null) {
                    fragmentManager.beginTransaction()
                            .add(R.id.movie_detail_container, new MovieDetailFragment(), DETAILFRAGMENT_TAG)
                            .commit();
                }
            } else {
                mTwoPane = false;
            }

        }

    /**
     * @brief On manu option creation
     * @param menu
     * @return
     */
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

    /**
     * @brief On item selection
     * @param item
     * @return
     */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.action_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    /**
     * @brif To load
     * @param movie
     */
        @Override
        public void loadItem(Movie movie) {
            if (mTwoPane) {
                Bundle args = new Bundle();
                args.putParcelable("movies_details", movie);

                MovieDetailFragment fragment = new MovieDetailFragment();
                fragment.setArguments(args);

                fragmentManager.beginTransaction()
                        .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                        .commit();
            } else {
                Intent intent = new Intent(this, MovieDetailActivity.class)
                        .putExtra("movies_details", movie);
                startActivity(intent);
            }
        }
}
