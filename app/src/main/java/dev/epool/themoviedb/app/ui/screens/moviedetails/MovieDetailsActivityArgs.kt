package dev.epool.themoviedb.app.ui.screens.moviedetails

import dev.epool.themoviedb.app.common.ActivityArgs
import dev.epool.themoviedb.app.ui.models.UiMovie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailsActivityArgs(
    val movie: UiMovie
) : ActivityArgs<MovieDetailsActivity>(MovieDetailsActivity::class.java)