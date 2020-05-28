package com.larsonapps.popularmovies.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieReviewStatsReviews {
    @Embedded
    MovieReviewEntity stat;
    @Relation(
            parentColumn = "statKeyId",
            entityColumn = "reviewStatKeyId"
    )
    List<MovieReviewEntity> reviews;
}
