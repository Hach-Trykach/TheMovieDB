package dev.epool.themoviedb.app.ui.screens.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.epool.themoviedb.app.common.TMDCoroutineViewModel
import dev.epool.themoviedb.app.data.MoviesRepository
import dev.epool.themoviedb.app.extensions.apiToDbMovies
import dev.epool.themoviedb.app.ui.models.UiMovie
import dev.epool.themoviedb.app.ui.models.dbToUiMovies
import dev.epool.themoviedb.db.entities.DbMovie
import kotlinx.coroutines.launch

class MoviesViewModel(private val moviesRepository: MoviesRepository) : TMDCoroutineViewModel() {

    private val mediatorLiveData = MediatorLiveData<MoviesViewState>()

    private val categoryMutableLiveData = MutableLiveData<DbMovie.Category>()
    private val moviesLiveData: LiveData<List<UiMovie>> =
        Transformations.switchMap(categoryMutableLiveData) { category ->
            Transformations.map(moviesRepository.getMoviesLocally(category)) { it.dbToUiMovies() }
        }
    val viewStateLiveData: LiveData<MoviesViewState> = mediatorLiveData

    init {
        mediatorLiveData.addSource(moviesLiveData) {
            mediatorLiveData.value = MoviesViewState.Success(it)
        }
    }

    override fun handleError(throwable: Throwable) {
        mediatorLiveData.value = MoviesViewState.Error(throwable)
    }

    fun loadMovies(category: DbMovie.Category) {
        launch {
            mediatorLiveData.value = MoviesViewState.Loading(true)
            categoryMutableLiveData.value = category
            val dbMovies = moviesRepository.getMoviesRemotely(category).apiToDbMovies(category)
            moviesRepository.insertMovies(*dbMovies)
            mediatorLiveData.value = MoviesViewState.Loading(false)
        }
    }

}
