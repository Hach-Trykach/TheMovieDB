package dev.epool.themoviedb.app.ui.screens.movies

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

class MoviesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockRepository: MoviesRepository

    private lateinit var viewModel: MoviesViewModel

    private val category = DbMovie.Category.POPULAR
    private val apiMovies = Constants.apiMovies
    private val dbMovies = apiMovies.apiToDbMovies(category).toList()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() = runBlocking {
        Dispatchers.setMain(Dispatchers.Unconfined)

        val liveData = MutableLiveData<List<DbMovie>>()
        liveData.value = dbMovies

        mockRepository = mock {
            on { getMoviesLocally(category) } doReturn liveData
            onBlocking { getMoviesRemotely(category) } doReturn apiMovies
        }

        viewModel = MoviesViewModel(mockRepository)
    }

    @Test
    fun testLoadMovies() {
        //arrange
        viewModel.viewStateLiveData.assertValues(
            MoviesViewState.Loading(true),
            MoviesViewState.Success(dbMovies.dbToUiMovies()),
            MoviesViewState.Loading(false)
        ) {
            //act
            viewModel.loadMovies(category)

            //assert
            verify(mockRepository).getMoviesLocally(category)
            verifyBlocking(mockRepository) { getMoviesRemotely(category) }
            verifyBlocking(mockRepository) { insertMovies(*dbMovies.toTypedArray()) }
        }
    }

}
