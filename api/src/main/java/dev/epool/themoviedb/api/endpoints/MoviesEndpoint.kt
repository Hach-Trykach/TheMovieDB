package dev.epool.themoviedb.api.endpoints

import dev.epool.themoviedb.api.models.ApiMovieDetails
import dev.epool.themoviedb.api.models.responses.ApiMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesEndpoint {

    companion object {

        private const val ENDPOINT = "movie/"

    }

    @GET("${ENDPOINT}popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): ApiMovieResponse

    @GET("${ENDPOINT}top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): ApiMovieResponse

    @GET("${ENDPOINT}upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 1): ApiMovieResponse

    @GET("$ENDPOINT{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): ApiMovieDetails

}