package dev.epool.themoviedb.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.ui.models.UiMovie
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val onClickListener: (View, UiMovie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val movies = mutableListOf<UiMovie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent, onClickListener)

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    fun setMovies(newMovies: List<UiMovie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    fun clear() {
        movies.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(
        viewGroup: ViewGroup,
        private val onClickListener: (View, UiMovie) -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.movie_item, viewGroup, false)
    ) {

        fun bind(movie: UiMovie) = with(itemView) {
            movieCardView.movie = movie
            movieCardView.setOnClickListener { onClickListener(this, movie) }
        }

    }

}