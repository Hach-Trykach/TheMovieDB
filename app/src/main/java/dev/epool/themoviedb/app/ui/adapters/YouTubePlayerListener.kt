package dev.epool.themoviedb.app.ui.adapters

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class YouTubePlayerListener(private val youTubeVideoId: String) : AbstractYouTubePlayerListener() {

    override fun onReady(youTubePlayer: YouTubePlayer) = youTubePlayer.cueVideo(youTubeVideoId, 0f)

}