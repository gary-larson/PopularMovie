package com.larsonapps.popularmovies.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieFavoriteVideosReviews {
    @Embedded MovieFavoriteEntity favorite;
    @Relation(
            parentColumn = "favoriteKeyId",
            entityColumn = "videoFavoriteKeyId"
    )
    List<MovieFavoriteVideoEntity> videos;
    @Relation(
            parentColumn = "favoriteKeyId",
            entityColumn = "statFavoriteKeyId"
    )
    List<MovieFavoriteReviewStatsReviews> statsReviews;
}
