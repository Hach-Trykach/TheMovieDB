package dev.epool.themoviedb.app.ui.screens.searchmovies

import android.widget.Toast
import androidx.core.view.isVisible
import dev.epool.themoviedb.app.ui.models.UiMovie
import kotlinx.android.synthetic.main.fragment_search_movies.*
import java.net.UnknownHostException

sealed class SearchMoviesViewState {

    abstract fun render(fragment: SearchMoviesFragment)

    data class Loading(private val isLoading: Boolean) : SearchMoviesViewState() {

        override fun render(fragment: SearchMoviesFragment) = with(fragment) {
            progressBar.isVisible = isLoading
        }

    }

    data class Success(
        private val query: String,
        private val movies: List<UiMovie>
    ) : SearchMoviesViewState() {

        override fun render(fragment: SearchMoviesFragment) = with(fragment) {
            adapter.setMovies(movies)
        }

    }

    data class Error(private val throwable: Throwable) : SearchMoviesViewState() {

        override fun render(fragment: SearchMoviesFragment) = with(fragment) {
            if (throwable !is UnknownHostException) {
                Toast.makeText(context, throwable.message, Toast.LENGTH_LONG).show()
                throwable.printStackTrace()
            }
            progressBar.isVisible = false
        }

    }

}