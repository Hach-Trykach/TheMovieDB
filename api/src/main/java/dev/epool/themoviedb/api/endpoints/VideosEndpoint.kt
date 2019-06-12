package dev.epool.themoviedb.api.endpoints

import dev.epool.themoviedb.api.models.responses.ApiVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideosEndpoint {

    companion object {

        private const val ENDPOINT = "movie/{movie_id}/videos"

    }

    @GET(ENDPOINT)
    suspend fun getVideosBy(@Path("movie_id") movieId: Int, @Query("page") page: Int = 1): ApiVideoResponse

}