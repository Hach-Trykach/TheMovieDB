package dev.epool.themoviedb.app.ui.screens.searchmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.epool.themoviedb.app.common.TMDCoroutineViewModel
import dev.epool.themoviedb.app.data.MoviesRepository
import dev.epool.themoviedb.app.extensions.apiToDbMovies
import dev.epool.themoviedb.app.ui.models.UiMovie
import dev.epool.themoviedb.app.ui.models.dbToUiMovies
import kotlinx.coroutines.launch

class SearchMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : TMDCoroutineViewModel() {

    private val mediatorLiveData = MediatorLiveData<SearchMoviesViewState>()

    private val queryMutableLiveData = MutableLiveData<String>()
    private val moviesLiveData: LiveData<Pair<String, List<UiMovie>>> =
        Transformations.switchMap(queryMutableLiveData) { query ->
            Transformations.map(moviesRepository.searchMoviesLocally(query)) { movies ->
                Pair(query, movies.dbToUiMovies())
            }
        }
    val viewStateLiveData: LiveData<SearchMoviesViewState> = mediatorLiveData

    init {
        mediatorLiveData.addSource(moviesLiveData) { (query, movies) ->
            mediatorLiveData.value = SearchMoviesViewState.Success(query, movies)
        }
    }

    override fun handleError(throwable: Throwable) {
        mediatorLiveData.value = SearchMoviesViewState.Error(throwable)
    }

    fun searchMoviesLocally(query: String) {
        queryMutableLiveData.value = query
    }

    fun searchMoviesRemotely(query: String) {
        launch {
            mediatorLiveData.value = SearchMoviesViewState.Loading(true)
            val remoteMovies = moviesRepository.searchMoviesRemotely(query)
            val dbMovies = remoteMovies.apiToDbMovies()
            moviesRepository.insertMovies(*dbMovies)
            mediatorLiveData.value = SearchMoviesViewState.Loading(false)
        }
    }

}
