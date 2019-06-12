package dev.epool.themoviedb.api.endpoints

import dev.epool.themoviedb.api.models.responses.ApiMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchEndpoint {

    companion object {

        private const val ENDPOINT = "search/"

    }

    @GET("${ENDPOINT}movie")
    suspend fun searchMovies(@Query("query") query: String, @Query("page") page: Int = 1): ApiMovieResponse

}