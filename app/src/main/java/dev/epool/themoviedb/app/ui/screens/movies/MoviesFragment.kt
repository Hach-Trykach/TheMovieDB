package dev.epool.themoviedb.app.ui.screens.movies

import android.os.Bundle
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.common.TMDBaseFragment
import dev.epool.themoviedb.app.extensions.viewModel
import dev.epool.themoviedb.app.ui.adapters.MoviesAdapter
import dev.epool.themoviedb.app.ui.screens.moviedetails.MovieDetailsActivityArgs
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlinx.android.synthetic.main.movies_fragment.*

class MoviesFragment : TMDBaseFragment<MoviesFragmentArgs>() {

    private lateinit var movieCardViewTransitionName: String

    val adapter = MoviesAdapter { view, movie ->
        MovieDetailsActivityArgs(movie).launch(
            requireActivity(),
            Pair.create(view.movieCardView, movieCardViewTransitionName)
        )
    }

    private val viewModel by lazy { viewModel<MoviesViewModel>() }

    override fun layoutId() = R.layout.movies_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieCardViewTransitionName = getString(R.string.movieCardView)

        moviesRecyclerView.layoutManager = LinearLayoutManager(context)
        moviesRecyclerView.adapter = adapter

        arguments?.category?.let { viewModel.loadMovies(it) }
        viewModel.viewStateLiveData.observe(this, Observer { it?.render(this) })
    }

}
