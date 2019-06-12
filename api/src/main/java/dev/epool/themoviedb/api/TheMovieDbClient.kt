package dev.epool.themoviedb.api

import dev.epool.themoviedb.api.endpoints.MoviesEndpoint
import dev.epool.themoviedb.api.endpoints.SearchEndpoint
import dev.epool.themoviedb.api.endpoints.VideosEndpoint
import kotlinx.serialization.UnstableDefault

class TheMovieDbClient private constructor(
    moviesEndpoint: MoviesEndpoint,
    videosEndpoint: VideosEndpoint,
    searchEndpoint: SearchEndpoint
) : MoviesEndpoint by moviesEndpoint,
    VideosEndpoint by videosEndpoint,
    SearchEndpoint by searchEndpoint {

    companion object {

        @UnstableDefault
        fun newInstance(apiKey: String) = with(newRetrofitInstance(apiKey)) {
            TheMovieDbClient(create(), create(), create())
        }

    }

}
