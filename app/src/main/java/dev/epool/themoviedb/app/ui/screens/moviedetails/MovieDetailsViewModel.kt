package dev.epool.themoviedb.app.ui.screens.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.epool.themoviedb.app.common.TMDCoroutineViewModel
import dev.epool.themoviedb.app.data.MoviesRepository
import dev.epool.themoviedb.app.extensions.apiToDbGenres
import dev.epool.themoviedb.app.extensions.dbToUiGenres
import dev.epool.themoviedb.app.ui.models.UiMovieDetails
import dev.epool.themoviedb.app.ui.models.apiToUiVideos
import dev.epool.themoviedb.db.entities.DbMovieDetail
import dev.epool.themoviedb.db.entities.toMovieGenreJoins
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository
) : TMDCoroutineViewModel() {

    private val mediatorLiveData = MediatorLiveData<MovieDetailsViewState>()

    private val movieIdMutableLiveData = MutableLiveData<Int>()
    private val movieDetailsLiveData: LiveData<UiMovieDetails> =
        Transformations.switchMap(movieIdMutableLiveData) { movieId ->
            Transformations.switchMap(moviesRepository.findMovieByIdLiveData(movieId)) { movie ->
                Transformations.map(moviesRepository.getGenresForMovie(movieId)) { genres ->
                    UiMovieDetails(movie.id, movie.movieDetail?.homepage, genres.dbToUiGenres())
                }
            }
        }
    val viewStateLiveData: LiveData<MovieDetailsViewState> = mediatorLiveData

    init {
        mediatorLiveData.addSource(movieDetailsLiveData) { movieDetails ->
            mediatorLiveData.value = MovieDetailsViewState.MovieDetails(movieDetails)
        }
    }

    override fun handleError(throwable: Throwable) {
        mediatorLiveData.value = MovieDetailsViewState.Error(throwable)
    }

    fun getMovieDetails(movieId: Int) {
        launch {
            movieIdMutableLiveData.value = movieId
            val remoteMovieDetail = moviesRepository.getMovieDetails(movieId)
            val dbMovie = moviesRepository.findMovieById(movieId)!!
            val dbMovieDetail = DbMovieDetail(remoteMovieDetail.homepage)
            val dbUpdatedMovie = dbMovie.copy(movieDetail = dbMovieDetail)
            val dbGenres = remoteMovieDetail.genres.apiToDbGenres()
            moviesRepository.insertMovies(dbUpdatedMovie)
            moviesRepository.insertGenres(*dbGenres.toTypedArray())
            val dbMovieGenreJoins = dbGenres.toMovieGenreJoins(dbMovie)
            moviesRepository.insertMovieGenreJoins(*dbMovieGenreJoins)
        }
    }

    fun getVideosByMovieId(movieId: Int) {
        launch {
            val videos = moviesRepository.getVideosByMovieId(movieId)
                .filter { it.isYouTube }
                .apiToUiVideos()
            mediatorLiveData.value = MovieDetailsViewState.VideosLoaded(videos)
        }
    }

}
