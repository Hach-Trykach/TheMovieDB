package dev.epool.themoviedb.db.entities

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import dev.epool.themoviedb.db.Constants
import dev.epool.themoviedb.db.TheMovieDatabase
import dev.epool.themoviedb.db.test
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: TheMovieDatabase
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(context, TheMovieDatabase::class.java)
            .build()
        movieDao = db.movieDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testFindById_Found() = runBlocking {
        //arrange
        val expectedMovie = Constants.movies.first()
        movieDao.insertAll(expectedMovie)

        //act
        val movie = movieDao.findById(expectedMovie.id)

        //assert
        assertEquals(expectedMovie, movie)
    }

    @Test
    fun testFindById_NotFound() = runBlocking {
        //arrange

        //act
        val movie = movieDao.findById(1)

        //assert
        assertNull(movie)
    }

    @Test
    fun testFindByIdLiveData_Found() = runBlocking {
        //arrange
        val expectedMovie = Constants.movies.first()
        movieDao.insertAll(expectedMovie)

        //act
        val movie = movieDao.findByIdLiveData(expectedMovie.id).test()

        //assert
        assertEquals(expectedMovie, movie)
    }

    @Test
    fun testFindByIdLiveData_NotFound() = runBlocking {
        //arrange

        //act
        val movie = movieDao.findByIdLiveData(1).test()

        //assert
        assertNull(movie)
    }

    @Test
    fun testInsertAll() = runBlocking {
        //arrange

        //act
        movieDao.insertAll(*Constants.movies)

        //assert
    }

    @Test
    fun testSearch_Found() = runBlocking {
        //arrange
        movieDao.insertAll(*Constants.movies)

        //act
        val movies = movieDao.search("magical").test()

        //assert
        assertTrue(movies?.isNotEmpty() == true)
    }

    @Test
    fun testSearch_NotFound() = runBlocking {
        //arrange
        movieDao.insertAll(*Constants.movies)

        //act
        val movies = movieDao.search("asdf").test()

        //assert
        assertTrue(movies?.isEmpty() == true)
    }

    @Test
    fun testFindByCategory_Found() = runBlocking {
        //arrange
        movieDao.insertAll(*Constants.movies)

        //act
        val movies = movieDao.findByCategory(DbMovie.Category.POPULAR).test()

        //assert
        assertTrue(movies?.isNotEmpty() == true)
    }

    @Test
    fun testFindByCategory_NotFound() = runBlocking {
        //arrange
        movieDao.insertAll(*Constants.movies)

        //act
        val movies = movieDao.findByCategory(DbMovie.Category.TOP_RATED).test()

        //assert
        assertTrue(movies?.isEmpty() == true)
    }

}
