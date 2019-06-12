package dev.epool.themoviedb.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "movie_genre_join",
    primaryKeys = ["movie_id", "genre_id", "movie_category"],
    indices = [
        Index(value = ["movie_id", "movie_category"], name = "movie_index"),
        Index(value = ["genre_id"], name = "genre_index")
    ],
    foreignKeys = [
        ForeignKey(
            entity = DbMovie::class,
            parentColumns = ["id", "category"],
            childColumns = ["movie_id", "movie_category"]
        ),
        ForeignKey(
            entity = DbGenre::class,
            parentColumns = ["id"],
            childColumns = ["genre_id"]
        )
    ]
)
data class DbMovieGenreJoin constructor(
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "genre_id") val genreId: Int,
    @ColumnInfo(name = "movie_category") val category: DbMovie.Category = DbMovie.Category.NONE
)