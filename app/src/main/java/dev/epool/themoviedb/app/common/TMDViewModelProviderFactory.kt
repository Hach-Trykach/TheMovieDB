package dev.epool.themoviedb.app.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.epool.themoviedb.api.TheMovieDbClient
import dev.epool.themoviedb.app.BuildConfig
import dev.epool.themoviedb.app.data.MoviesRepository
import dev.epool.themoviedb.app.ui.screens.moviedetails.MovieDetailsViewModel
import dev.epool.themoviedb.app.ui.screens.movies.MoviesViewModel
import dev.epool.themoviedb.app.ui.screens.searchmovies.SearchMoviesViewModel
import dev.epool.themoviedb.db.TheMovieDatabase

class TMDViewModelProviderFactory(context: Context) : ViewModelProvider.Factory {

    private val theMovieDbClient = TheMovieDbClient.newInstance(BuildConfig.THE_MOVIE_DB_API_KEY)
    private val db = TheMovieDatabase.getDatabase(context)
    private val moviesRepository = MoviesRepository(theMovieDbClient, db)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MoviesViewModel::class.java) ->
            MoviesViewModel(moviesRepository)
        modelClass.isAssignableFrom(MovieDetailsViewModel::class.java) ->
            MovieDetailsViewModel(moviesRepository)
        modelClass.isAssignableFrom(SearchMoviesViewModel::class.java) ->
            SearchMoviesViewModel(moviesRepository)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T

}