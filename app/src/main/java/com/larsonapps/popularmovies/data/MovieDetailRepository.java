package com.larsonapps.popularmovies.data;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.larsonapps.popularmovies.R;
import com.larsonapps.popularmovies.utilities.MovieExecutor;
import com.larsonapps.popularmovies.utilities.MovieJsonUtilities;
import com.larsonapps.popularmovies.utilities.MovieNetworkUtilities;
import com.larsonapps.popularmovies.utilities.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Class to handle retrieving movie detail data
 */
public class MovieDetailRepository {
    // Declare variables
    private static String mApiKey;
    private Application mApplication;
    private MovieExecutor executor;
    private String mErrorMessage;
    private static int mMovieId;
    // Live data variables
    private static MutableLiveData<Result<MovieDetailInfo>> mMovieDetailInfo = new MutableLiveData<>();
    private static MutableLiveData<MovieDetailSummary> mMovieDetailSummary = new MutableLiveData<>();
    private static MutableLiveData<Result<MovieDetailReview>> mMovieDetailReview = new MutableLiveData<>();
    private static MutableLiveData<List<MovieDetailVideo>> mMovieDetailVideoList =
            new MutableLiveData<>();

    // TODO Add room logic

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
    }

    /**
     * Method to get movie detail info for the movie detail info live data
     */
    public MutableLiveData<Result<MovieDetailInfo>> getMovieDetailInfo(final int movieId, String mType) {
        // save movieID
        mMovieId = movieId;
        if (mType.equals(mApplication.getString(R.string.setting_movie_list_favorite_value))) {
            // TODO process favorite with room
            Result<MovieDetailInfo> result = new Result.Error<>(
                    mApplication.getString(R.string.type_favorite_none_message));
        } else {
            // TODO process with room
            // start background task
            retrieveMovieDetails(mApiKey, movieId, new RepositoryCallback<MovieDetails>() {
                @Override
                public void onComplete(Result<MovieDetails> result) {
                    if (result instanceof Result.Success) {
                        Result.Success<MovieDetails> resultSuccess =
                                (Result.Success<MovieDetails>) result;
                        MovieDetails movieDetails = resultSuccess.data;
                        // add movieId to movie detail info
                        movieDetails.getMovieDetailInfo().setmMovieId(movieId);
                        Result<MovieDetailInfo> movieDetailInfoResult = new
                                Result.Success<>(movieDetails.getMovieDetailInfo());
                        mMovieDetailInfo.postValue(movieDetailInfoResult);
                        MovieDetailSummary movieDetailSummary = movieDetails.getMovieDetailSummary();
                        mMovieDetailSummary.postValue(movieDetailSummary);
                        // send results through live data movie detail reviews
                        Result<MovieDetailReview> movieDetailReviewResult = new
                                Result.Success<>(movieDetails.getMovieDetailReview());
                        mMovieDetailReview.postValue(movieDetailReviewResult);
                        // send results through live data movie detail videos
                        List<MovieDetailVideo> movieDetailVideos = movieDetails.getVideoList();
                        mMovieDetailVideoList.postValue(movieDetailVideos);
                    } else {
                        Result.Error<MovieDetails> resulterror = (Result.Error<MovieDetails>) result;
                        Result<MovieDetailInfo> movieDetailInfoResult = new
                                Result.Error<>(resulterror.mErrorMessage);
                    }
                }
            });
        }
        return mMovieDetailInfo;
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

    public void getMovieDetailReviewNextPage(int page) {
        retrieveMovieDetailReviews(mApiKey, mMovieId, page, new RepositoryCallback<MovieDetailReview>() {
            @Override
            public void onComplete(Result<MovieDetailReview> result) {
                mMovieDetailReview.postValue(result);
            }
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(mApplication.getAssets()
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
        executor.execute(new Runnable() {
            @Override
            public void run() {
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
        executor.execute(new Runnable() {
            @Override
            public void run() {
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