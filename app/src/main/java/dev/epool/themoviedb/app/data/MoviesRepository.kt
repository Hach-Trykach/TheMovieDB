package dev.epool.themoviedb.app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import dev.epool.themoviedb.api.TheMovieDbClient
import dev.epool.themoviedb.db.TheMovieDatabase
import dev.epool.themoviedb.db.entities.DbGenre
import dev.epool.themoviedb.db.entities.DbMovie
import dev.epool.themoviedb.db.entities.DbMovieGenreJoin

class MoviesRepository(
    private val theMovieDbClient: TheMovieDbClient,
    private val theMovieDb: TheMovieDatabase
) {

    suspend fun findMovieById(movieId: Int): DbMovie? = theMovieDb.movieDao().findById(movieId)

    fun findMovieByIdLiveData(movieId: Int): LiveData<DbMovie> =
        theMovieDb.movieDao().findByIdLiveData(movieId)

    fun getMoviesLocally(category: DbMovie.Category): LiveData<List<DbMovie>> =
        theMovieDb.movieDao().findByCategory(category)

    fun searchMoviesLocally(query: String): LiveData<List<DbMovie>> =
        Transformations.map(theMovieDb.movieDao().search(query)) { dbMovies ->
            dbMovies.map { it.copy(category = DbMovie.Category.NONE) }.distinct()
        }

    fun getGenresForMovie(movieId: Int): LiveData<List<DbGenre>> =
        theMovieDb.movieGenreJoinDao().getGenresForMovie(movieId)

    suspend fun insertMovies(vararg movies: DbMovie) = theMovieDb.movieDao().insertAll(*movies)

    suspend fun insertGenres(vararg genres: DbGenre) = theMovieDb.genreDao().insertAll(*genres)

    suspend fun insertMovieGenreJoins(vararg movieGenreJoins: DbMovieGenreJoin) =
        theMovieDb.movieGenreJoinDao().insertAll(*movieGenreJoins)

    suspend fun getMoviesRemotely(category: DbMovie.Category) = when (category) {
        DbMovie.Category.POPULAR -> theMovieDbClient.getPopularMovies().results
        DbMovie.Category.TOP_RATED -> theMovieDbClient.getTopRatedMovies().results
        DbMovie.Category.UPCOMING -> theMovieDbClient.getUpcomingMovies().results
        DbMovie.Category.NONE -> emptyList()
    }

    suspend fun getMovieDetails(movieId: Int) = theMovieDbClient.getMovieDetails(movieId)

    suspend fun getVideosByMovieId(movieId: Int) = theMovieDbClient.getVideosBy(movieId).results

    suspend fun searchMoviesRemotely(query: String) = theMovieDbClient.searchMovies(query).results

}