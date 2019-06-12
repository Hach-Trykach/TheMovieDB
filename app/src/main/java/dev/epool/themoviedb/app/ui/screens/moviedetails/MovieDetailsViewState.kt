package dev.epool.themoviedb.app.ui.screens.moviedetails

import android.widget.Toast
import androidx.core.view.isVisible
import dev.epool.themoviedb.app.extensions.addChipsWithText
import dev.epool.themoviedb.app.extensions.openUrl
import dev.epool.themoviedb.app.ui.models.UiMovieDetails
import dev.epool.themoviedb.app.ui.models.UiVideo
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.net.UnknownHostException


sealed class MovieDetailsViewState {

    abstract fun render(activity: MovieDetailsActivity)

    data class VideosLoaded(private val videos: List<UiVideo>) : MovieDetailsViewState() {

        override fun render(activity: MovieDetailsActivity) = with(activity) {
            videosViewGroup.isVisible = videos.isNotEmpty()
            adapter.addVideos(videos)
        }

    }

    data class MovieDetails(private val movieDetails: UiMovieDetails) : MovieDetailsViewState() {

        override fun render(activity: MovieDetailsActivity) = with(activity) {
            movieDetails.homepage?.let { homepageUrl ->
                with(videoFloatingActionButton) {
                    show()
                    setOnClickListener { openUrl(homepageUrl) }
                }
            }
            genresViewGroup.isVisible = movieDetails.genres.isNotEmpty()
            genresChipGroup.addChipsWithText(movieDetails.genres.map { it.name })
        }

    }

    data class Error(private val throwable: Throwable) : MovieDetailsViewState() {

        override fun render(activity: MovieDetailsActivity) = with(activity) {
            if (throwable !is UnknownHostException) {
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        }

    }

}