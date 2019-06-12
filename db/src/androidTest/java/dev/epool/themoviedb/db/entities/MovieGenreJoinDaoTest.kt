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
import org.junit.Assert.assertEquals
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
class MovieGenreJoinDaoTest {

    private lateinit var movieDao: MovieDao
    private lateinit var genreDao: GenreDao
    private lateinit var movieGenreJoinDao: MovieGenreJoinDao
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
        genreDao = db.genreDao()
        movieGenreJoinDao = db.movieGenreJoinDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAll() = runBlocking {
        //arrange
        val movie = Constants.movies.first()
        movieDao.insertAll(movie)
        val genre = Constants.genres.first()
        genreDao.insertAll(genre)

        //act
        movieGenreJoinDao.insertAll(DbMovieGenreJoin(movie.id, genre.id))

        //assert
    }

    @Test
    fun testGetGenresForMovie() = runBlocking {
        //arrange
        val movie = Constants.movies.first()
        movieDao.insertAll(movie)

        genreDao.insertAll(*Constants.genres)

        movieGenreJoinDao.insertAll(
            *Constants.genres
                .map { DbMovieGenreJoin(movie.id, it.id) }
                .toTypedArray()
        )

        //act
        val genre = movieGenreJoinDao.getGenresForMovie(movie.id).test()

        //assert
        assertEquals(Constants.genres.size, genre?.size)
    }

}
