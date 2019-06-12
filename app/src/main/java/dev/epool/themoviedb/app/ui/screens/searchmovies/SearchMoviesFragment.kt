package dev.epool.themoviedb.app.ui.screens.searchmovies

import android.content.Intent
import android.os.Bundle
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferfalk.simplesearchview.SimpleSearchView
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.common.CustomLifeCycleListener
import dev.epool.themoviedb.app.common.TMDBaseFragment
import dev.epool.themoviedb.app.extensions.customize
import dev.epool.themoviedb.app.extensions.editText
import dev.epool.themoviedb.app.extensions.viewModel
import dev.epool.themoviedb.app.ui.adapters.MoviesAdapter
import dev.epool.themoviedb.app.ui.screens.moviedetails.MovieDetailsActivityArgs
import kotlinx.android.synthetic.main.fragment_search_movies.*
import kotlinx.android.synthetic.main.movie_item.view.*

class SearchMoviesFragment : TMDBaseFragment<SearchMoviesFragmentArgs>(),
    SimpleSearchView.OnQueryTextListener, CustomLifeCycleListener {

    private lateinit var movieCardViewTransitionName: String

    val adapter = MoviesAdapter { view, movie ->
        MovieDetailsActivityArgs(movie).launch(
            requireActivity(),
            Pair.create(view.movieCardView, movieCardViewTransitionName)
        )
    }

    private val viewModel by lazy { viewModel<SearchMoviesViewModel>() }

    override fun layoutId() = R.layout.fragment_search_movies

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchView.showSearch(false)
        searchView.customize()
        searchView.setOnQueryTextListener(this)

        movieCardViewTransitionName = getString(R.string.movieCardView)

        moviesRecyclerView.layoutManager = LinearLayoutManager(context)
        moviesRecyclerView.adapter = adapter

        viewModel.viewStateLiveData.observe(this, Observer { it?.render(this) })
    }

    override fun shown() {
        searchView?.editText?.requestFocus()
    }

    override fun customOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        searchView.onActivityResult(requestCode, resultCode, data)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.searchMoviesRemotely(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        if (newText.isNotEmpty()) {
            viewModel.searchMoviesLocally(newText)
        } else {
            adapter.clear()
        }
        return true
    }

    override fun onQueryTextCleared(): Boolean {
        adapter.clear()
        return true
    }

}
