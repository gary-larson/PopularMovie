package com.larsonapps.popularmovies.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieVideosReviews {
    @Embedded
    MovieEntity favorite;
    @Relation(
            parentColumn = "favoriteKeyId",
            entityColumn = "videoFavoriteKeyId"
    )
    List<MovieVideoEntity> videos;
    @Relation(
            parentColumn = "favoriteKeyId",
            entityColumn = "statFavoriteKeyId"
    )
    List<MovieReviewStatsReviews> statsReviews;
}
