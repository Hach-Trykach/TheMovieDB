package dev.epool.themoviedb.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import java.util.*

@Entity(
    tableName = "movie",
    primaryKeys = ["id", "category"]
)
data class DbMovie(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "release_date") val releaseDate: Date?,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "category") val category: Category = Category.NONE,
    @Embedded val movieDetail: DbMovieDetail? = null
) {

    enum class Category { POPULAR, TOP_RATED, UPCOMING, NONE }

}