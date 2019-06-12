package dev.epool.themoviedb.api

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TheMovieDbClientTest {

    private lateinit var theMovieDbClient: TheMovieDbClient

    @UnstableDefault
    @Before
    fun setup() {
        theMovieDbClient = TheMovieDbClient.newInstance(System.getenv("THE_MOVIE_DB_API_KEY"))
    }

    @Test
    fun testGetPopularMovies() = runBlocking {
        //arrange

        //act
        val response = theMovieDbClient.getPopularMovies()

        //assert
        assertNotNull(response)
        val popularMovies = response.results
        assertNotNull(popularMovies)
        assertTrue(popularMovies.isNotEmpty())
    }

    @Test
    fun testGetTopRatedMovies() = runBlocking {
        //arrange

        //act
        val response = theMovieDbClient.getTopRatedMovies()

        //assert
        assertNotNull(response)
        val topRatedMovies = response.results
        assertNotNull(topRatedMovies)
        assertTrue(topRatedMovies.isNotEmpty())
    }

    @Test
    fun testGetUpcomingMovies() = runBlocking {
        //arrange

        //act
        val response = theMovieDbClient.getUpcomingMovies()

        //assert
        assertNotNull(response)
        val upcomingMovies = response.results
        assertNotNull(upcomingMovies)
        assertTrue(upcomingMovies.isNotEmpty())
    }

    @Test
    fun testGetMovieDetails() = runBlocking {
        //arrange
        val movieId = 420817

        //act
        val movieDetails = theMovieDbClient.getMovieDetails(movieId)

        //assert
        assertNotNull(movieDetails)
        assertEquals(movieId, movieDetails.id)
        assertNotNull(movieDetails.homepage)
        assertTrue(movieDetails.genres.isNotEmpty())
    }

    @Test
    fun testGetVideosByMovieId() = runBlocking {
        //arrange
        val movieId = 420817

        //act
        val response = theMovieDbClient.getVideosBy(movieId)

        //assert
        assertNotNull(response)
        val videos = response.results
        assertNotNull(videos)
        assertTrue(videos.isNotEmpty())
    }

    @Test
    fun testSearchMovies() = runBlocking {
        //arrange
        val query = "aladdin"

        //act
        val response = theMovieDbClient.searchMovies(query)

        //assert
        assertNotNull(response)
        val movies = response.results
        assertNotNull(movies)
        assertTrue(movies.isNotEmpty())
    }

}
