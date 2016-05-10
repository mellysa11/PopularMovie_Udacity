package com.example.android.popularmovies_stage2.AsyncTasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies_stage2.Config.AppConfig;
import com.example.android.popularmovies_stage2.DataObjects.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mellysamargaritasusilo on 5/9/16.
 */
public class FetchMoviesCompsAsync extends AsyncTask<String, Void, String> {

    private final String LOG_TAG = FetchMoviesCompsAsync.class.getSimpleName();
    private final String MOVIE_BASE = "http://api.themoviedb.org/3/movie/";
    private Movie movie;
    private String componentType;

    /***
     * @bbrief Constructor
     * @param movie
     * @param componentType
     */
    public FetchMoviesCompsAsync(Movie movie, String componentType){
        this.movie = movie;
        this.componentType = componentType;
    }

    /**
     * @brief Fetch in background
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {

        if (params.length != 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {

            Uri builtUri = Uri.parse(MOVIE_BASE + movie.getMovieId() + "/" + componentType ).buildUpon()
                    .appendQueryParameter("api_key", AppConfig.API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            return buffer.toString();

        } catch (IOException e) {

            Log.e(LOG_TAG, "Error ", e);
            return null;

        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

    }

    /**
     * @brief
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {

        if(componentType.equals("reviews")){
            movie.setReviews(result);
        }else {
            movie.setMoviePreviews(result);
        }

    }

}

