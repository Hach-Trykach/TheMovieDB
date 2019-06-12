package dev.epool.themoviedb.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import dev.epool.themoviedb.app.R
import dev.epool.themoviedb.app.ui.models.UiVideo
import kotlinx.android.synthetic.main.video_item.view.*

class YouTubeVideosAdapter(
    private val lifecycle: Lifecycle
) : RecyclerView.Adapter<YouTubeVideosAdapter.ViewHolder>() {

    private val videos = mutableListOf<UiVideo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent, lifecycle)

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(videos[position])

    fun addVideos(newVideos: List<UiVideo>) {
        videos.clear()
        videos.addAll(newVideos)
        notifyDataSetChanged()
    }

    class ViewHolder(viewGroup: ViewGroup, lifecycle: Lifecycle) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.video_item, viewGroup, false)
    ) {

        private var oldListener: YouTubePlayerListener? = null

        init {
            lifecycle.addObserver(itemView.videoPlayerView)
        }

        fun bind(video: UiVideo) = with(itemView) {
            oldListener?.let { videoPlayerView.removeYouTubePlayerListener(it) }
            val newListener = YouTubePlayerListener(video.key)
            videoPlayerView.addYouTubePlayerListener(newListener)
            oldListener = newListener
        }

    }

}