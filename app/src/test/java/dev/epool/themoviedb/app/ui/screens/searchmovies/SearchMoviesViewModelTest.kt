package dev.epool.themoviedb.app.ui.screens.searchmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import dev.epool.themoviedb.app.Constants
import dev.epool.themoviedb.app.assertValues
import dev.epool.themoviedb.app.data.MoviesRepository
import dev.epool.themoviedb.app.extensions.apiToDbMovies
import dev.epool.themoviedb.app.ui.models.dbToUiMovies
import dev.epool.themoviedb.db.entities.DbMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

class SearchMoviesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockRepository: MoviesRepository

    private lateinit var viewModel: SearchMoviesViewModel

    private val query = "Query"
    private val apiMovies = Constants.apiMovies
    private val dbMovies = apiMovies.apiToDbMovies(DbMovie.Category.NONE).toList()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)

        val liveData = MutableLiveData<List<DbMovie>>()
        liveData.value = dbMovies

        mockRepository = mock {
            on { searchMoviesLocally(query) } doReturn liveData
            onBlocking { searchMoviesRemotely(query) } doReturn apiMovies
        }

        viewModel = SearchMoviesViewModel(mockRepository)
    }

    @Test
    fun testSearchMoviesLocally() {
        //arrange
        viewModel.viewStateLiveData.assertValues(
            SearchMoviesViewState.Success(query, dbMovies.dbToUiMovies())
        ) {
            //act
            viewModel.searchMoviesLocally(query)

            //assert
            verify(mockRepository).searchMoviesLocally(query)
        }
    }

    @Test
    fun testSearchMoviesRemotely() {
        //arrange
        viewModel.viewStateLiveData.assertValues(
            SearchMoviesViewState.Loading(true),
            SearchMoviesViewState.Success(query, dbMovies.dbToUiMovies()),
            SearchMoviesViewState.Loading(false)
        ) {
            //act
            viewModel.searchMoviesRemotely(query)

            //assert
            verifyBlocking(mockRepository) { searchMoviesRemotely(query) }
            verifyBlocking(mockRepository) { insertMovies(*dbMovies.toTypedArray()) }
        }
    }

}