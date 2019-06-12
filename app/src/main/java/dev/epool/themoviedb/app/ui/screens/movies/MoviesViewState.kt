package dev.epool.themoviedb.app.ui.screens.movies

import android.widget.Toast
import androidx.core.view.isVisible
import dev.epool.themoviedb.app.ui.models.UiMovie
import kotlinx.android.synthetic.main.movies_fragment.*
import java.net.UnknownHostException

sealed class MoviesViewState {

    abstract fun render(fragment: MoviesFragment)

    data class Loading(private val isLoading: Boolean) : MoviesViewState() {

        override fun render(fragment: MoviesFragment) = with(fragment) {
            progressBar.isVisible = isLoading
        }

    }

    data class Success(private val movies: List<UiMovie>) : MoviesViewState() {

        override fun render(fragment: MoviesFragment) = with(fragment) {
            adapter.setMovies(movies)
        }

    }

    data class Error(private val throwable: Throwable) : MoviesViewState() {

        override fun render(fragment: MoviesFragment) = with(fragment) {
            if (throwable !is UnknownHostException) {
                Toast.makeText(context, throwable.message, Toast.LENGTH_LONG).show()
            }
            progressBar.isVisible = false
        }

    }

}