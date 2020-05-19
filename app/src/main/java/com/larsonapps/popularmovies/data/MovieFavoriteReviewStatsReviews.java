package com.larsonapps.popularmovies.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieFavoriteReviewStatsReviews {
    @Embedded
    MovieFavoriteReviewEntity stat;
    @Relation(
            parentColumn = "statKeyId",
            entityColumn = "reviewStatKeyId"
    )
    List<MovieFavoriteReviewEntity> reviews;
}
