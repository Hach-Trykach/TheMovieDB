package dev.epool.themoviedb.app.ui.models

import android.os.Parcelable
import dev.epool.themoviedb.api.models.ApiMovieDetails
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiMovieDetails(
    val id: Int,
    val homepage: String?,
    val genres: List<UiGenre>
) : Parcelable

fun ApiMovieDetails.apiToUiMovieDetails() =
    UiMovieDetails(id, homepage, genres.map { it.apiToUiGenre() })