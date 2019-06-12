package dev.epool.themoviedb.app.ui.screens.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import dev.epool.themoviedb.api.models.ApiGenre
import dev.epool.themoviedb.api.models.ApiMovieDetails
import dev.epool.themoviedb.api.models.ApiVideo
import dev.epool.themoviedb.app.Constants
import dev.epool.themoviedb.app.assertValues
import dev.epool.themoviedb.app.data.MoviesRepository
import dev.epool.themoviedb.app.extensions.apiToDbGenres
import dev.epool.themoviedb.app.extensions.apiToDbMovies
import dev.epool.themoviedb.app.extensions.dbToUiGenres
import dev.epool.themoviedb.app.ui.models.UiMovieDetails
import dev.epool.themoviedb.app.ui.models.apiToUiVideos
import dev.epool.themoviedb.db.entities.DbGenre
import dev.epool.themoviedb.db.entities.DbMovie
import dev.epool.themoviedb.db.entities.DbMovieDetail
import dev.epool.themoviedb.db.entities.toMovieGenreJoins
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

class MovieDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockRepository: MoviesRepository

    private lateinit var viewModel: MovieDetailsViewModel

    private val homepage = "http://darkphoenix.com"
    private val dbMovie =
        Constants.apiMovies.apiToDbMovies().first().copy(movieDetail = DbMovieDetail(homepage))
    private val apiMovieDetails = ApiMovieDetails(
        dbMovie.id,
        homepage,
        listOf(
            ApiGenre(878, "Science Fiction"),
            ApiGenre(28, "Action")
        )
    )
    private val dbUpdatedMovie = dbMovie.copy(movieDetail = DbMovieDetail(apiMovieDetails.homepage))
    private val dbGenres = apiMovieDetails.genres.apiToDbGenres()
    private val dbMovieGenreJoins = dbGenres.toMovieGenreJoins(dbMovie)
    private val apiVideos = emptyList<ApiVideo>()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        val movieLiveData = MutableLiveData<DbMovie>()
        movieLiveData.value = dbMovie
        val genresLiveData = MutableLiveData<List<DbGenre>>()
        genresLiveData.value = dbGenres

        mockRepository = mock {
            on { findMovieByIdLiveData(dbMovie.id) } doReturn movieLiveData
            on { getGenresForMovie(dbMovie.id) } doReturn genresLiveData
            onBlocking { getMovieDetails(dbMovie.id) } doReturn apiMovieDetails
            onBlocking { findMovieById(dbMovie.id) } doReturn dbMovie
            onBlocking { getVideosByMovieId(dbMovie.id) } doReturn apiVideos
        }

        viewModel = MovieDetailsViewModel(mockRepository)
    }

    @Test
    fun testGetMovieDetails() {
        //arrange
        viewModel.viewStateLiveData.assertValues(
            MovieDetailsViewState.MovieDetails(
                UiMovieDetails(
                    dbMovie.id,
                    dbUpdatedMovie.movieDetail?.homepage,
                    dbGenres.dbToUiGenres()
                )
            )
        ) {
            //act
            viewModel.getMovieDetails(dbMovie.id)

            //assert
            verify(mockRepository).findMovieByIdLiveData(dbMovie.id)
            verify(mockRepository).getGenresForMovie(dbMovie.id)
            verifyBlocking(mockRepository) { getMovieDetails(dbMovie.id) }
            verifyBlocking(mockRepository) { findMovieById(dbMovie.id) }
            verifyBlocking(mockRepository) { insertMovies(dbUpdatedMovie) }
            verifyBlocking(mockRepository) { insertGenres(*dbGenres.toTypedArray()) }
            verifyBlocking(mockRepository) { insertMovieGenreJoins(*dbMovieGenreJoins) }
        }
    }

    @Test
    fun testGetVideosByMovieId() {
        //arrange
        viewModel.viewStateLiveData.assertValues(
            MovieDetailsViewState.VideosLoaded(apiVideos.apiToUiVideos())
        ) {
            //act
            viewModel.getVideosByMovieId(dbMovie.id)

            //assert
            verifyBlocking(mockRepository) { getVideosByMovieId(dbMovie.id) }
        }
    }

}