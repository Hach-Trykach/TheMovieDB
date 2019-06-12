package dev.epool.themoviedb.app.ui.models

import android.os.Parcelable
import dev.epool.themoviedb.api.models.ApiMovie
import dev.epool.themoviedb.api.models.ApiMovieHelper
import dev.epool.themoviedb.app.extensions.format
import dev.epool.themoviedb.db.entities.DbMovie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiMovie(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    override val releaseDate: String?,
    val overview: String,
    override val posterPath: String?,
    override val backdropPath: String?
) : ApiMovieHelper, Parcelable

fun List<ApiMovie>.apiToUiMovies() = map {
    with(it) {
        UiMovie(id, title, voteAverage, releaseDate, overview, posterPath, backdropPath)
    }
}

fun List<DbMovie>.dbToUiMovies() = map {
    with(it) {
        UiMovie(id, title, voteAverage, releaseDate?.format(), overview, posterPath, backdropPath)
    }
}