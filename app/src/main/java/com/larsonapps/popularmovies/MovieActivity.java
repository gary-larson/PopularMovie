package com.larsonapps.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.larsonapps.popularmovies.utilities.NetworkUtilities;
import com.larsonapps.popularmovies.viewmodels.MovieListViewModel;

public class MovieActivity extends AppCompatActivity implements
        MovieItemFragment.OnListFragmentInteractionListener {
    public static int mNumberHorizontalImages;
    public static int mNumberVerticalImages;
    public static String mPosterSize;
    MovieListViewModel mViewModel;
    private String mTitle;
    private String mSortTitle;
    private boolean isPreviousEnabled;
    private boolean isNextEnabled;
    private MenuItem mSortMenuItem;
    private MenuItem mPreviousMenuItem;
    private MenuItem mNextMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mNumberVerticalImages = getResources().getInteger(R.integer.number_vertical_posters);
        mNumberHorizontalImages = getResources().getInteger(R.integer.number_horizontal_posters);
        mPosterSize = getResources().getString(R.string.poster_size);
        mViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        mSortTitle = "Sort Rating";
        mTitle = "Pop Movies";
        isPreviousEnabled = false;
        isNextEnabled = true;
    }


    @Override
    public void onListFragmentInteraction(int position) {
        Toast.makeText(this, "You have pressed movie in position: " + position,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Inflate the main menu
     * @param menu to inflate
     * @return true to indicate menu inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mSortMenuItem = menu.getItem(0);
        mPreviousMenuItem = menu.getItem(1);
        mNextMenuItem =  menu.getItem(2);
        mSortMenuItem.setTitle(mSortTitle);
        mPreviousMenuItem.setEnabled(isPreviousEnabled);
        mNextMenuItem.setEnabled(isNextEnabled);
        return true;
    }

    /**
     * Method to process items clicked on menu
     * @param item to process
     * @return whether item is handled by this method or super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        switch (menuItemThatWasSelected) {
            case R.id.action_sort:
                // if sort is selected switch movies
                if (item.getTitle().equals("Sort Rating")) {
                    // get Highest Rated Movies
                    mSortTitle = "Sort Popular";
                    mTitle = "Rated Movies";
                    mViewModel.setmPage(1);
                    mViewModel.setmType(NetworkUtilities.HIGHEST_RATED_REQUEST_URL);
                    mViewModel.retrieveMovieMain();
                } else {
                    // Get Most Popular Movies
                    mSortTitle = "Sort Rating";
                    mTitle = "Pop Movies";
                    mViewModel.setmPage(1);
                    mViewModel.setmType(NetworkUtilities.POPULAR_REQUEST_URL);
                    mViewModel.retrieveMovieMain();
                }
                isPreviousEnabled = false;
                mPreviousMenuItem.setEnabled(isPreviousEnabled);
                item.setTitle(mSortTitle);
                setTitle(mTitle);
                return true;
            case R.id.action_previous_page:
                // Get previous page of Movies
                mViewModel.setmPage(mViewModel.getmPage() - 1);
                if (mViewModel.getmPage() == 1) {
                    isPreviousEnabled = false;
                    item.setEnabled(isPreviousEnabled);
                }
                isNextEnabled = true;
                mNextMenuItem.setEnabled(isNextEnabled);
                mViewModel.retrieveMovieMain();
                return true;
            case R.id.action_next_page:
                // Get Next Page of Movies
                mViewModel.setmPage(mViewModel.getmPage() + 1);
                isPreviousEnabled = true;
                mPreviousMenuItem.setEnabled(isPreviousEnabled);
                if (mViewModel.getmPage() == mViewModel.getTotalPages()) {
                    isNextEnabled = false;
                    item.setEnabled(isNextEnabled);
                }
                mViewModel.retrieveMovieMain();
                return true;
            case R.id.action_about:
                // Show about activity
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
