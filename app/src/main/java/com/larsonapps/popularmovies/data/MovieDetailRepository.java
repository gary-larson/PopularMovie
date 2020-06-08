package com.larsonapps.popularmovies.data;

import android.app.Application;

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
import com.larsonapps.popularmovies.database.MovieRoomDatabase;
import com.larsonapps.popularmovies.utilities.MovieExecutor;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.larsonapps.popularmovies.utilities.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to handle retrieving movie detail data
 */
public class MovieDetailRepository {
    // Declare constants
    public final String LIST_TYPE_REVIEW = "review";
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
    // Live data variables
    private static MutableLiveData<Result<MovieDetailInfo>> mMovieDetailInfo = new MutableLiveData<>();
    private static MutableLiveData<MovieDetailSummary> mMovieDetailSummary = new MutableLiveData<>();
    private static MutableLiveData<Result<MovieDetailReview>> mMovieDetailReview = new MutableLiveData<>();
    private static MutableLiveData<List<MovieDetailVideo>> mMovieDetailVideoList =
            new MutableLiveData<>();
// TODO trailers not getting saved
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
                // create variable to hold movie ddetail review data
                MovieDetailReview movieDetailReview = new MovieDetailReview();
                // get control information for review
                MovieControlEntity movieControlEntity =
                        mMovieControlDao.getMovieControlEntry(LIST_TYPE_REVIEW);
                // transfer data from entity to movie detail review
                movieDetailReview.setPage(movieControlEntity.getHighestPage());
                movieDetailReview.setTotalPages(movieControlEntity.getTotalPages());
                // get review list from database
                List<MovieDetailReviewListEntity> movieReviewListEntity =
                        mMovieDetailReviewListDao.getAllReviews(movieId);
                // create variable to hold review list
                List<MovieDetailReviewResult> movieDetailReviewResults = new ArrayList<>();
                // transfer data from entity to revies list
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
                        MovieDetails movieDetails1 = resultSuccess.data;
                        // add movieId to movie detail info
                        movieDetails1.getMovieDetailInfo().setMovieId(movieId);
                        Result<MovieDetailInfo> movieDetailInfoResult = new
                                Result.Success<>(movieDetails1.getMovieDetailInfo());
                        mMovieDetailInfo.postValue(movieDetailInfoResult);
                        MovieDetailSummary movieDetailSummary = movieDetails1.
                                getMovieDetailSummary();
                        mMovieDetailSummary.postValue(movieDetailSummary);
                        // add data to the movie detail entity variable
                        MovieDetailEntity movieDetailEntity = new MovieDetailEntity(movieId,
                                movieDetails1.getMovieDetailInfo().getTitle(),
                                movieDetails1.getMovieDetailInfo().getBackdropPath(),
                                null,
                                movieDetails1.getMovieDetailInfo().getOverview(),
                                movieDetails1.getMovieDetailSummary().getReleaseDate(),
                                movieDetails1.getMovieDetailSummary().getRuntime(),
                                movieDetails1.getMovieDetailSummary().getVoteAverage());
                        // insert or update data in database
                        if (mMovieDetailDao.isMovieDetailEntry(movieId)) {
                            mMovieDetailDao.updateMovieDetailEntry(movieDetailEntity);
                        } else {
                            mMovieDetailDao.insertMovieDetailEntry(movieDetailEntity);
                        }
                        // send results through live data movie detail reviews
                        Result<MovieDetailReview> movieDetailReviewResult = new
                                Result.Success<>(movieDetails1.getMovieDetailReview());
                        mMovieDetailReview.postValue(movieDetailReviewResult);
                        // Add entry for movie control
                        MovieControlEntity movieControlEntity = new
                                MovieControlEntity(LIST_TYPE_REVIEW,
                                new Date(System.currentTimeMillis()),
                                movieDetails1.getMovieDetailReview().getPage(),
                                movieDetails1.getMovieDetailReview().getTotalPages(), movieId);
                        // add reviews to database
                        if (mMovieControlDao.isEntry(movieId, LIST_TYPE_REVIEW)) {
                            mMovieControlDao.updateControl(movieControlEntity);
                            // delete the movie review list
                            mMovieDetailReviewListDao.deleteAllReviews(movieId);
                            // get movie detail review list
                            List<MovieDetailReviewResult> movieDetailReviewResults =
                                    movieDetails1.getMovieDetailReview().getReviewList();
                            // Create review entity list to hold movie review list
                            List<MovieDetailReviewListEntity> movieDetailReviewListEntities =
                                    getMovieDetailReviewListEntities(movieDetailReviewResults, movieId);
                            // add data to the movie detail review list entity
                            mMovieDetailReviewListDao.insertAllReviewEntries(
                                    movieDetailReviewListEntities);
                        } else {
                            mMovieControlDao.insertControl(movieControlEntity);
                            List<MovieDetailReviewResult> movieDetailReviewResults =
                                    movieDetails1.getMovieDetailReview().getReviewList();
                            // Create review entity list to hold movie review list
                            List<MovieDetailReviewListEntity> movieDetailReviewListEntities =
                                    getMovieDetailReviewListEntities(movieDetailReviewResults, movieId);
                            // add data to the movie detail review list entity
                            mMovieDetailReviewListDao.insertAllReviewEntries(
                                    movieDetailReviewListEntities);
                        }
                        // send results through live data movie detail videos
                        List<MovieDetailVideo> movieDetailVideos = movieDetails1.getVideoList();
                        mMovieDetailVideoList.postValue(movieDetailVideos);
                        // add videos to database
                        if (mMovieDetailVideoListDao.isVideos(movieId)) {
                            // delete all videos
                            mMovieDetailVideoListDao.deleteAllVideos(movieId);
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
                        }
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

    private List<MovieDetailReviewListEntity> getMovieDetailReviewListEntities(List<MovieDetailReviewResult> movieDetailReviewResults, int movieId) {
        List<MovieDetailReviewListEntity> movieDetailReviewListEntities =
                new ArrayList<>();
        // Transfer data from variable to entity
        for (int i = 0; i < movieDetailReviewResults.size(); i++) {
            MovieDetailReviewListEntity movieDetailReviewListEntity =
                    new MovieDetailReviewListEntity(movieId,
                            movieDetailReviewResults.get(i).getAuthor(),
                            movieDetailReviewResults.get(i).getContent(),
                            movieDetailReviewResults.get(i).getUrl());
            movieDetailReviewListEntities.add(movieDetailReviewListEntity);
        }
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

    public void getMovieDetailReviewNextPage(final int page) {
        retrieveMovieDetailReviews(mApiKey, mMovieId, page, result -> {
            if (result instanceof Result.Success) {
                Result.Success<MovieDetailReview> resultSuccess =
                        (Result.Success<MovieDetailReview>) result;
                // Check page number
                MovieControlEntity movieControlEntity =
                        mMovieControlDao.getMovieControlEntry(LIST_TYPE_REVIEW);
                List<MovieDetailReviewListEntity> movieDetailReviewListEntities =
                        new ArrayList<>();
                if (movieControlEntity != null && page <= movieControlEntity.getHighestPage()) {
                    mMovieDetailReviewListDao.deleteAllReviews(mMovieId);

                } else {
                    movieDetailReviewListEntities.addAll(
                            mMovieDetailReviewListDao.getAllReviews(mMovieId));
                }
                List<MovieDetailReviewResult> movieDetailReviewResults =
                        resultSuccess.data.getReviewList();
                // transfer data to entity
                for (int i = 0; i < movieDetailReviewResults.size(); i++){
                    MovieDetailReviewListEntity movieDetailReviewListEntity =
                            new MovieDetailReviewListEntity(mMovieId,
                                    movieDetailReviewResults.get(i).getAuthor(),
                                    movieDetailReviewResults.get(i).getContent(),
                                    movieDetailReviewResults.get(i).getUrl());
                    movieDetailReviewListEntities.add(movieDetailReviewListEntity);
                }
                if (movieControlEntity == null) {
                    movieControlEntity = new MovieControlEntity(LIST_TYPE_REVIEW,
                            new Date(System.currentTimeMillis()),
                            resultSuccess.data.getPage(),
                            resultSuccess.data.getTotalPages(),
                            mMovieId);
                } else {
                    movieControlEntity.setHighestPage(resultSuccess.data.getPage());
                    movieControlEntity.setDownloadDate(new Date(System.currentTimeMillis()));
                    movieControlEntity.setTotalPages(resultSuccess.data.getTotalPages());
                }
                mMovieControlDao.updateControl(movieControlEntity);
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
                // save list
                resultSuccess.data.setReviewList(movieDetailReviewResults);
                // create a result success variable and store data
                result = new Result.Success<>(resultSuccess.data);
            }
            //
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

//// TODO save favorite images
////    2. To use the Picasso for saving image file, you need to define a Target class.
//// This method creates a target object that you can use with Picasso.
//// Target is an interface defined in Picasso’s library.
//// You can define this method in your Activity class or an util class.
//    private Target saveImage(Context context, final String imageDir, final String imageName) {
//        Log.d("picassoImageTarget", " picassoImageTarget");
//        ContextWrapper cw = new ContextWrapper(context);
//        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
//        return new Target() {
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        final File myImageFile = new File(directory, imageName); // Create image file
//                        FileOutputStream fos = null;
//                        try {
//                            fos = new FileOutputStream(myImageFile);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } finally {
//                            try {
//                                fos.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());
//
//                    }
//                }).start();
//            }
//
//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//            }
//
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                if (placeHolderDrawable != null) {
//                }
//            }
//        };
//    }


//3. Now let’s use Picasso to download the image using the Target defined above.
// We use imageDir as the image directory, and my_image.jpeg for the image name,
// the image will be saved to /data/data/com.your.app.package.path/app_imageDir/my_image.png
//
//Picasso.with(this).load(anImageUrl).into(saveImage(getApplicationContext(),
//          "imageDir", "my_image.jpeg"));

    // 4. To load the image after it’s downloaded.
//    ContextWrapper cw = new ContextWrapper(mApplication.getApplicationContext());
//    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//    File myImageFile = new File(directory, "my_image.jpeg");
//    Picasso.get().load(myImageFile).into(ivImage);

   // 5. To delete the image from the internal storage.
//   ContextWrapper cw = new ContextWrapper(mApplication.getApplicationContext());
//    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//    File myImageFile = new File(directory, "my_image.jpeg");
//if (myImageFile.delete()) log("image on the disk deleted successfully!");
}