package dev.epool.themoviedb.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class DbGenre(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String
)

fun List<DbGenre>.toMovieGenreJoins(movie: DbMovie) = map {
    DbMovieGenreJoin(movie.id, it.id, movie.category)
}.toTypedArray()