package dev.epool.themoviedb.app.ui.screens.moviedetails

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.epool.themoviedb.api.models.ApiMovieHelper
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.common.TMDBaseActivity
import dev.epool.themoviedb.app.extensions.asPager
import dev.epool.themoviedb.app.extensions.loadUrl
import dev.epool.themoviedb.app.extensions.setTitleTextColor
import dev.epool.themoviedb.app.extensions.viewModel
import dev.epool.themoviedb.app.ui.adapters.YouTubeVideosAdapter
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : TMDBaseActivity<MovieDetailsActivityArgs>() {

    val adapter = YouTubeVideosAdapter(lifecycle)

    private val viewModel by lazy { viewModel<MovieDetailsViewModel>() }

    override fun layoutId() = R.layout.activity_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videosRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        videosRecyclerView.adapter = adapter
        videosRecyclerView.asPager()

        arguments?.movie?.let { movie ->
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.title = movie.title
            }
            collapsingToolbarLayout.setTitleTextColor(Color.WHITE)

            movieBackdropImageView.loadUrl(movie.getBackdropUrl(ApiMovieHelper.ImageSize.W500))

            movieCardView.movie = movie

            viewModel.getMovieDetails(movie.id)
            viewModel.getVideosByMovieId(movie.id)
        }
        viewModel.viewStateLiveData.observe(this, Observer { it?.render(this) })
    }

}
