package dev.epool.themoviedb.db.entities

import androidx.room.ColumnInfo

data class DbMovieDetail(
    @ColumnInfo(name = "homepage") val homepage: String?
)