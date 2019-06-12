package dev.epool.themoviedb.app.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.extensions.loadUrl
import dev.epool.themoviedb.app.ui.models.UiMovie
import kotlinx.android.synthetic.main.widget__movie_card_view.view.*

class MovieCardView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attr, defStyleAttr) {

    var movie: UiMovie? = null
        @SuppressLint("SetTextI18n")
        set(value) {
            value?.getPosterUrl()?.let { moviePosterImageView.loadUrl(it) }
            val voteAveragePercentage = value?.voteAverage?.let { (it * 10.0).toInt() }
            movieVoteAverageProgressBar.progress = voteAveragePercentage ?: 0
            movieVoteAverageTextView.text = voteAveragePercentage?.let { "$it%" }
            movieTitle.text = value?.title
            movieReleaseDate.text = value?.releaseDate
            movieOverviewTextView.text = value?.overview
            field = value
        }

    init {
        View.inflate(context, R.layout.widget__movie_card_view, this)
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) =
        cardView.setOnClickListener(onClickListener)

}