package com.larsonapps.popularmovies.data;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.MutableLiveData;

import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.database.MovieControlDao;
import com.larsonapps.popularmovies.database.MovieControlEntity;
import com.larsonapps.popularmovies.database.MovieDetailDao;
import com.larsonapps.popularmovies.database.MovieDetailEntity;
import com.larsonapps.popularmovies.database.MovieDetailReviewListDao;
import com.larsonapps.popularmovies.database.MovieDetailReviewListEntity;
import com.larsonapps.popularmovies.database.MovieDetailVideoListDao;
import com.larsonapps.popularmovies.database.MovieDetailVideoListEntity;
import com.larsonapps.popularmovies.database.MovieListDao;
import com.larsonapps.popularmovies.database.MovieListEntity;
import com.larsonapps.popularmovies.database.MovieRoomDatabase;
import com.larsonapps.popularmovies.utilities.MovieExecutor;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.larsonapps.popularmovies.utilities.Result;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class to handle retrieving movie detail data
 */
public class MovieDetailRepository {
    // Declare constants
    public final String LIST_TYPE_REVIEW = "review";
    public final String IMAGE_DIRECTORY = "images";
    // Declare variables
    private static String mApiKey;
    private Application mApplication;
    private MovieExecutor executor;
    private String mErrorMessage;
    private static int mMovieId;
    // Database variables
    private MovieControlDao mMovieControlDao;
    private MovieDetailDao mMovieDetailDao;
    private MovieDetailReviewListDao mMovieDetailReviewListDao;
    private MovieDetailVideoListDao mMovieDetailVideoListDao;
    private MovieListDao mMovieListDao;
    // Live data variables
    private static MutableLiveData<Result<MovieDetailInfo>> mMovieDetailInfo = new MutableLiveData<>();
    private static MutableLiveData<MovieDetailSummary> mMovieDetailSummary = new MutableLiveData<>();
    private static MutableLiveData<Result<MovieDetailReview>> mMovieDetailReview = new MutableLiveData<>();
    private static MutableLiveData<List<MovieDetailVideo>> mMovieDetailVideoList =
            new MutableLiveData<>();

    /**
     * Constructor for movie detail repository
     *
     * @param application used to access data
     */
    public MovieDetailRepository(Application application) {
        mApplication = application;
        mApiKey = loadApiKey();
        mErrorMessage = mApplication.getString(R.string.error_message);
        executor = new MovieExecutor();
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieControlDao = db.movieControlDao();
        mMovieDetailDao = db.movieDetailDao();
        mMovieDetailReviewListDao = db.movieDetailReviewListDao();
        mMovieDetailVideoListDao = db.movieDetailVideoListDao();
        mMovieListDao = db.movieListDao();
    }

    /**
     * Method to get movie detail info for the movie detail info live data
     */
    public MutableLiveData<Result<MovieDetailInfo>> getMovieDetailInfo(final int movieId) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            // save movieID
            mMovieId = movieId;
            // get movie details from room database
            MovieDetailEntity movieDetails = mMovieDetailDao.getMovieDetails(movieId);
            // if data return
            if (movieDetails != null) {
                // create variable to hold movie detail info
                MovieDetailInfo movieDetailInfo = new MovieDetailInfo();
                // transfer data from entity variable to movie detail info
                movieDetailInfo.setBackdropPath(movieDetails.getBackdropPath());
                movieDetailInfo.setImagePath(movieDetails.getImagePath());
                movieDetailInfo.setMovieId(movieDetails.getMovieId());
                movieDetailInfo.setOverview(movieDetails.getOverview());
                movieDetailInfo.setTitle(movieDetails.getTitle());
                // create a success result to pass through live data
                Result.Success<MovieDetailInfo> infoResultSuccess =  new Result.Success<>(
                        movieDetailInfo);
                // store data in live data variable
                mMovieDetailInfo.postValue(infoResultSuccess);
                // create variable to hold movie detail summary
                MovieDetailSummary movieDetailSummary = new MovieDetailSummary();
                // transfer data from entity to movie detail summary
                movieDetailSummary.setReleaseDate(movieDetails.getReleaseDate());
                movieDetailSummary.setRuntime(movieDetails.getRuntime());
                movieDetailSummary.setVoteAverage(movieDetails.getVoteAverage());
                // transfer data in live data variable
                mMovieDetailSummary.postValue(movieDetailSummary);
                // create variable to hold movie detail review data
                MovieDetailReview movieDetailReview = new MovieDetailReview();
                // get control information for review
                MovieControlEntity movieControlEntity =
                        mMovieControlDao.getMovieReviewControlEntry(LIST_TYPE_REVIEW, movieId);
                // transfer data from entity to movie detail review
                if (movieControlEntity != null) {
                    movieDetailReview.setPage(movieControlEntity.getHighestPage());
                    movieDetailReview.setTotalPages(movieControlEntity.getTotalPages());
                }
                // get review list from database
                List<MovieDetailReviewListEntity> movieReviewListEntity =
                        mMovieDetailReviewListDao.getAllReviews(movieId);
                // create variable to hold review list
                List<MovieDetailReviewResult> movieDetailReviewResults = new ArrayList<>();
                // transfer data from entity to review list
                for (int i = 0; i < movieReviewListEntity.size(); i++) {
                    MovieDetailReviewResult movieDetailReviewResult = new MovieDetailReviewResult();
                    movieDetailReviewResult.setAuthor(movieReviewListEntity.get(i).getAuthor());
                    movieDetailReviewResult.setContent(movieReviewListEntity.get(i).getContent());
                    movieDetailReviewResult.setUrl((movieReviewListEntity.get(i).getUrl()));
                    movieDetailReviewResults.add(movieDetailReviewResult);
                }
                // move review list to movie detail review
                movieDetailReview.setReviewList(movieDetailReviewResults);
                // create a success result to pass through live data
                Result.Success<MovieDetailReview> reviewResultSuccess = new
                        Result.Success<>(movieDetailReview);
                // transfer data to live data variable
                mMovieDetailReview.postValue(reviewResultSuccess);
                // get detail video list from database
                List<MovieDetailVideoListEntity> movieVideoListEntity =
                        mMovieDetailVideoListDao.getAllVideos(movieId);
                // create variable to hold detail video list
                List<MovieDetailVideo> movieDetailVideos = new ArrayList<>();
                // transfer data from entity to detail video list
                for (int i=0; i< movieVideoListEntity.size(); i++) {
                    MovieDetailVideo movieDetailVideo = new MovieDetailVideo();
                    movieDetailVideo.setKey(movieVideoListEntity.get(i).getKey());
                    movieDetailVideo.setName(movieVideoListEntity.get(i).getName());
                    movieDetailVideo.setSite(movieVideoListEntity.get(i).getSite());
                    movieDetailVideos.add(movieDetailVideo);
                }
                // transfer data to live data variable
                mMovieDetailVideoList.postValue(movieDetailVideos);
            } else {
                // start background task
                retrieveMovieDetails(mApiKey, movieId, result -> {
                    if (result instanceof Result.Success) {
                        Result.Success<MovieDetails> resultSuccess =
                                (Result.Success<MovieDetails>) result;
                        MovieDetails movieDetailsResult = resultSuccess.data;
                        // add movieId to movie detail info
                        movieDetailsResult.getMovieDetailInfo().setMovieId(movieId);
                        Result<MovieDetailInfo> movieDetailInfoResult = new
                                Result.Success<>(movieDetailsResult.getMovieDetailInfo());
                        mMovieDetailInfo.postValue(movieDetailInfoResult);
                        MovieDetailSummary movieDetailSummary = movieDetailsResult.
                                getMovieDetailSummary();
                        mMovieDetailSummary.postValue(movieDetailSummary);
                        // add data to the movie detail entity variable
                        MovieDetailEntity movieDetailEntity = new MovieDetailEntity(movieId,
                                movieDetailsResult.getMovieDetailInfo().getTitle(),
                                movieDetailsResult.getMovieDetailInfo().getBackdropPath(),
                                null,
                                movieDetailsResult.getMovieDetailInfo().getOverview(),
                                movieDetailsResult.getMovieDetailSummary().getReleaseDate(),
                                movieDetailsResult.getMovieDetailSummary().getRuntime(),
                                movieDetailsResult.getMovieDetailSummary().getVoteAverage());
                        // insert or update data in database
                        if (mMovieDetailDao.isMovieDetailEntry(movieId)) {
                            mMovieDetailDao.updateMovieDetailEntry(movieDetailEntity);
                        } else {
                            mMovieDetailDao.insertMovieDetailEntry(movieDetailEntity);
                        }
                        // send results through live data movie detail reviews
                        Result<MovieDetailReview> movieDetailReviewResult = new
                                Result.Success<>(movieDetailsResult.getMovieDetailReview());
                        mMovieDetailReview.postValue(movieDetailReviewResult);
                        // Add entry for movie control
                        MovieControlEntity movieControlEntity = new
                                MovieControlEntity(LIST_TYPE_REVIEW,
                                new Date(System.currentTimeMillis()),
                                movieDetailsResult.getMovieDetailReview().getPage(),
                                movieDetailsResult.getMovieDetailReview().getTotalPages(), movieId);
                        // add reviews to database
                        if (mMovieControlDao.isEntry(movieId, LIST_TYPE_REVIEW)) {
                            mMovieControlDao.updateControl(movieControlEntity);
                            // delete the movie review list
                            mMovieDetailReviewListDao.deleteAllReviews(movieId);
                        } else {
                            mMovieControlDao.insertControl(movieControlEntity);
                        }
                        // get movie detail review list
                        List<MovieDetailReviewResult> movieDetailReviewResults =
                                movieDetailsResult.getMovieDetailReview().getReviewList();
                        // Create review entity list to hold movie review list
                        List<MovieDetailReviewListEntity> movieDetailReviewListEntities =
                                getMovieDetailReviewListEntities(movieDetailReviewResults, movieId);
                        // add data to the movie detail review list entity
                        mMovieDetailReviewListDao.insertAllReviewEntries(
                                movieDetailReviewListEntities);
                        // send results through live data movie detail videos
                        List<MovieDetailVideo> movieDetailVideos = movieDetailsResult.getVideoList();
                        mMovieDetailVideoList.postValue(movieDetailVideos);
                        // add videos to database
                        if (mMovieDetailVideoListDao.isVideos(movieId)) {
                            // delete all videos
                            mMovieDetailVideoListDao.deleteAllVideos(movieId);
                        }
                        // Create entity list to hold data
                        List<MovieDetailVideoListEntity> movieDetailVideoListEntities =
                                new ArrayList<>();
                        // Trasfer data to entity
                        for (int i = 0; i < movieDetailVideos.size(); i++) {
                            MovieDetailVideoListEntity movieDetailVideoListEntity =
                                    new MovieDetailVideoListEntity(movieId,
                                            movieDetailVideos.get(i).getKey(),
                                            movieDetailVideos.get(i).getName(),
                                            movieDetailVideos.get(i).getSite());
                            movieDetailVideoListEntities.add(movieDetailVideoListEntity);
                        }
                        // add all videos
                        mMovieDetailVideoListDao.insertAllVideos(movieDetailVideoListEntities);
                    } else {
                        Result.Error<MovieDetails> resulterror = (Result.Error<MovieDetails>)
                                result;
                        Result.Error<MovieDetailInfo> movieDetailInfoError =
                                new Result.Error<>(resulterror.mErrorMessage);
                        mMovieDetailInfo.postValue(movieDetailInfoError);
                    }
                });
            }
        });
        // send results through live data
        return mMovieDetailInfo;
    }

    /**
     * Method to transfer Review resiults list to entity list
     * @param movieDetailReviewResults to transfer to entity list
     * @param movieId of the movie in question
     * @return entity list
     */
    private List<MovieDetailReviewListEntity> getMovieDetailReviewListEntities(
            List<MovieDetailReviewResult> movieDetailReviewResults, int movieId) {
        // Create a review entity list to return
        List<MovieDetailReviewListEntity> movieDetailReviewListEntities =
                new ArrayList<>();
        // loop through review result list
        for (int i = 0; i < movieDetailReviewResults.size(); i++) {
            // Create entity entry and populate with review result data
            MovieDetailReviewListEntity movieDetailReviewListEntity =
                    new MovieDetailReviewListEntity(movieId,
                            movieDetailReviewResults.get(i).getAuthor(),
                            movieDetailReviewResults.get(i).getContent(),
                            movieDetailReviewResults.get(i).getUrl());
            // add entity entry to entity list
            movieDetailReviewListEntities.add(movieDetailReviewListEntity);
        }
        // return rntity list
        return movieDetailReviewListEntities;
    }

    /**
     * Method to get movie detail summary for the movie detail summary live data
     */
    public MutableLiveData<MovieDetailSummary> getMovieDetailSummary() {
        // return movie detail summary data
        return mMovieDetailSummary;
    }

    /**
     * Method to get movie detail reviews for the movie detail review live data
     */
    public MutableLiveData<Result<MovieDetailReview>> getMovieDetailReview() {
        // return movie detail summary data
        return mMovieDetailReview;
    }

    /**
     * Method to get movie detail videos for the movie detail video live data
     */
    public MutableLiveData<List<MovieDetailVideo>> getMovieDetailVideoList() {
        // return movie detail summary data
        return mMovieDetailVideoList;
    }

    /**
     * Method to add an additional review page to database
     * @param page number to add
     */
    public void getMovieDetailReviewNextPage(final int page) {
        retrieveMovieDetailReviews(mApiKey, mMovieId, page, result -> {
            // test result
            if (result instanceof Result.Success) {
                // get results from result success
                Result.Success<MovieDetailReview> resultSuccess =
                        (Result.Success<MovieDetailReview>) result;
                // Check page numberGet movie control entity
                MovieControlEntity movieControlEntity =
                        mMovieControlDao.getMovieReviewControlEntry(LIST_TYPE_REVIEW, mMovieId);
                // Create a review entity list for room database
                List<MovieDetailReviewListEntity> movieDetailReviewListEntities =
                        new ArrayList<>();
                // test for movie control entity and if page is new or replacement
                if (movieControlEntity != null && page <= movieControlEntity.getHighestPage()) {
                    // delete all review entries to be replaced
                    mMovieDetailReviewListDao.deleteAllReviews(mMovieId);
                } else {
                    // add all review entries from room database to entity list
                    movieDetailReviewListEntities.addAll(
                            mMovieDetailReviewListDao.getAllReviews(mMovieId));
                }
                // Create review result list and populate with review from internet
                List<MovieDetailReviewResult> movieDetailReviewResults =
                        resultSuccess.data.getReviewList();
                // Add result review results to review entity list
                for (int i = 0; i < movieDetailReviewResults.size(); i++){
                    MovieDetailReviewListEntity movieDetailReviewListEntity =
                            new MovieDetailReviewListEntity(mMovieId,
                                    movieDetailReviewResults.get(i).getAuthor(),
                                    movieDetailReviewResults.get(i).getContent(),
                                    movieDetailReviewResults.get(i).getUrl());
                    movieDetailReviewListEntities.add(movieDetailReviewListEntity);
                }
                // test movie control entry
                if (movieControlEntity == null) {
                    // Create control entry and populate
                    movieControlEntity = new MovieControlEntity(LIST_TYPE_REVIEW,
                            new Date(System.currentTimeMillis()),
                            resultSuccess.data.getPage(),
                            resultSuccess.data.getTotalPages(),
                            mMovieId);
                    // insert control entry in room database
                    mMovieControlDao.insertControl(movieControlEntity);
                } else {
                    // populate control entry with new information
                    movieControlEntity.setHighestPage(resultSuccess.data.getPage());
                    movieControlEntity.setDownloadDate(new Date(System.currentTimeMillis()));
                    movieControlEntity.setTotalPages(resultSuccess.data.getTotalPages());
                    // update control entity in room database
                    mMovieControlDao.updateControl(movieControlEntity);
                }
                // Insert all review entity entries into room database
                mMovieDetailReviewListDao.insertAllReviewEntries(movieDetailReviewListEntities);
                // covert new review list back to data
                movieDetailReviewResults.clear();
                for (int i = 0; i < movieDetailReviewListEntities.size(); i++) {
                    MovieDetailReviewResult movieDetailReviewResult =
                            new MovieDetailReviewResult();
                    movieDetailReviewResult.setAuthor(movieDetailReviewListEntities.get(i).getAuthor());
                    movieDetailReviewResult.setContent(movieDetailReviewListEntities.get(i).getContent());
                    movieDetailReviewResult.setUrl(movieDetailReviewListEntities.get(i).getUrl());
                    movieDetailReviewResults.add(movieDetailReviewResult);
                }
                // create a result success variable and store data
                resultSuccess.data.setReviewList(movieDetailReviewResults);
            }
            // send review result list though live data
            mMovieDetailReview.postValue(result);
        });
    }

    /**
     * Method to get the api key from the assets folder
     *
     * @return api key or null if not found
     */
    private String loadApiKey() {
        String apiKey = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(mApplication.
                    getAssets()
                    .open("tmdb_key.txt")));
            apiKey = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiKey;
    }

    /**
     * Method to run background task to get movie details
     * @param apiKey required for access
     * @param movieId of the movie to get details for
     * @param callback to send the results to
     */
    public void retrieveMovieDetails (
            final String apiKey, final int movieId,
            final RepositoryCallback<MovieDetails> callback
    ) {
        executor.execute(() -> {
            // build url
            URL movieRequestUrl = MovieNetworkUtilities.buildDetailsUrl(apiKey, movieId);

            try {
                // attempt to get movie information
                String jsonMovieResponse = MovieNetworkUtilities
                        .getResponseFromHttpUrl(movieRequestUrl);
                // if null cancel task (Unknown error)
                if (jsonMovieResponse == null) {
                    Result<MovieDetails> errorResult = new Result.Error<>(mErrorMessage);
                    callback.onComplete(errorResult);
                }
                // return Json decoded movie Information
                Result<MovieDetails> result = MovieJsonUtilities
                        .getMovieDetails(jsonMovieResponse);
                callback.onComplete(result);
            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                Result<MovieDetails> errorResult = new Result.Error<>(mErrorMessage);
                callback.onComplete(errorResult);
            }
        });
    }

    /**
     * Method to run background task to get additional movie details reviews
     * @param apiKey required for access
     * @param movieId of the movie to get details for
     * @param page of reviews to get
     * @param callback to send the results to
     */
    public void retrieveMovieDetailReviews (
            final String apiKey, final int movieId, final int page,
            final RepositoryCallback<MovieDetailReview> callback
    ) {
        executor.execute(() -> {
            // build url
            URL movieRequestUrl = MovieNetworkUtilities.buildReviewsUrl(apiKey, movieId, page);

            try {
                // attempt to get movie information
                String jsonMovieResponse = MovieNetworkUtilities
                        .getResponseFromHttpUrl(movieRequestUrl);
                // if null cancel task (Unknown error)
                if (jsonMovieResponse == null) {
                    Result<MovieDetailReview> errorResult = new Result.Error<>(mErrorMessage);
                    callback.onComplete(errorResult);
                }
                // return Json decoded movie Information
                Result<MovieDetailReview> result = MovieJsonUtilities
                        .getMovieDetailReview(jsonMovieResponse);
                callback.onComplete(result);
            } catch (Exception e) {
                e.printStackTrace();
                // in case of an error return null
                Result<MovieDetailReview> errorResult = new Result.Error<>(mErrorMessage);
                callback.onComplete(errorResult);
            }
        });
    }

    public void addFavorite(int movieId, String posterPath, String backdropPath) {
        // create url
        String urlString;
        String tempString = String.format(Locale.getDefault(), "backdrop%d", movieId);
        // Set whether or not to use ssl based on API build
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            urlString = MovieNetworkUtilities.POSTER_BASE_HTTPS_URL;
        } else{
            urlString = MovieNetworkUtilities.POSTER_BASE_HTTP_URL;
        }
        String url = urlString + mApplication.getString(R.string.backdrop_size) + backdropPath;
        Picasso.get().load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(saveImage(tempString, false));
        tempString = String.format(Locale.getDefault(), "poster%d", movieId);
        url = urlString + mApplication.getString(R.string.poster_size) + posterPath;
        Picasso.get().load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(saveImage(tempString, true));
    }

    public void removeFavorite(int movieId, String listImagePath, String detailImagePath) {
        // start on database thread
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            // delete list image
            if (deleteFile(listImagePath)) {
                // get list entity from room database
                MovieListEntity movieListEntity = mMovieListDao.getMovieListEntry(movieId);
                // clear image path
                movieListEntity.setImagePath(null);
                // clear favorite order
                movieListEntity.setFavoriteOrder(0);
                // save list entity to room database
                mMovieListDao.updateMovieListEntry(movieListEntity);
            }

            // delete detail image
            if (deleteFile(detailImagePath)) {
                // get detail entity from room database
                MovieDetailEntity movieDetailEntity = mMovieDetailDao.getMovieDetails(movieId);
                // clear detail image path
                movieDetailEntity.setImagePath(null);
                // save detail entity to room database
                mMovieDetailDao.updateMovieDetailEntry(movieDetailEntity);
                // update live data
                // test for success
                if (mMovieDetailInfo.getValue() instanceof Result.Success) {
                    // get result
                    Result.Success<MovieDetailInfo> result = (Result.Success<MovieDetailInfo>)
                            mMovieDetailInfo.getValue();
                    // set detail image path to null
                    result.data.setImagePath(null);
                    // send live data
                    mMovieDetailInfo.postValue(result);
                    mMovieDetailSummary.postValue(mMovieDetailSummary.getValue());
                }
            }
        });
    }

    private boolean deleteFile (String imagePath) {
        // get context wrapper
        ContextWrapper contextWrapper = new ContextWrapper(mApplication.getApplicationContext());
        // set directory
        File directory = contextWrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE);
        // declare variable
        String fileName;
        // extract file name from image path
        if (imagePath.indexOf("poster") > 0) {
            fileName = imagePath.substring(imagePath.indexOf("poster"));
        } else {
            fileName = imagePath.substring(imagePath.indexOf("backdrop"));
        }
        // get image file
        File imageFile = new File(directory, fileName);
        // delete image file
        return imageFile.delete();
    }

    public void saveFavoriteBackdropImage (int movieId, String imagePath) {
        // start on database thread
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            MovieDetailEntity movieDetailEntity = mMovieDetailDao.getMovieDetails(movieId);
            // test if good (should always be)
            if (movieDetailEntity != null) {
                //add backdrop image path to movie detail entity
                movieDetailEntity.setImagePath(imagePath);
                // update movie detail entity
                mMovieDetailDao.updateMovieDetailEntry(movieDetailEntity);
                if (mMovieDetailInfo.getValue() instanceof Result.Success) {
                    // get result
                    Result.Success<MovieDetailInfo> result = (Result.Success<MovieDetailInfo>)
                            mMovieDetailInfo.getValue();
                    // set detail image path to image path
                    result.data.setImagePath(imagePath);
                    // send live data
                    mMovieDetailInfo.postValue(result);
                    mMovieDetailSummary.postValue(mMovieDetailSummary.getValue());
                }
            }
        });
    }

    private void saveFavoritePosterImage (int movieId, String imagePath) {
        // start on database thread
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            // Get movie list entry and movie detail entity
            MovieListEntity movieListEntity = mMovieListDao.getMovieListEntry(movieId);
            if (movieListEntity != null) {
                // add poster image path to movie list entity
                movieListEntity.setImagePath(imagePath);
                // get and set movie favorite order
                movieListEntity.setFavoriteOrder(mMovieListDao.getMaximumFavoriteOrder() + 1);
                // update movie list dao
                mMovieListDao.updateMovieListEntry(movieListEntity);
            }
        });
    }

    /**
     * Method to save image file with picasso
     * @param imageName to save
     * @return target
     */
    private Target saveImage(final String imageName, boolean isPoster) {
        // Get context
        Context context = mApplication.getApplicationContext();
        // Put context in a wrapper
        ContextWrapper contextWrapper = new ContextWrapper(context);
        // Get directory
        final File directory = contextWrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE);
        // save file
        return new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // Create file that will be saved
                final File imageFile = new File(directory, imageName);
                // Create filestream
                FileOutputStream fileOutputStream = null;
                try {
                    // make new filestream
                    fileOutputStream = new FileOutputStream(imageFile);
                    // compress and save imagefile
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    if (isPoster) {
                        saveFavoritePosterImage(mMovieId, imageFile.getAbsolutePath());
                    } else {
                        saveFavoriteBackdropImage(mMovieId, imageFile.getAbsolutePath());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fileOutputStream != null) {
                            // close filestream
                            fileOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            /**
             * Method to deal with failure
             * @param e excaption thrown
             * @param errorDrawable that failed
             */
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            /**
             * Method to deal with placeholder (Not used)
             * @param placeHolderDrawable to set
             */
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }
}