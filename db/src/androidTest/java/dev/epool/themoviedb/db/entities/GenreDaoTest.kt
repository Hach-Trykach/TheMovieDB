package dev.epool.themoviedb.db.entities

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import dev.epool.themoviedb.db.Constants
import dev.epool.themoviedb.db.TheMovieDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
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
class GenreDaoTest {

    private lateinit var genreDao: GenreDao
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
        genreDao = db.genreDao()
        movieDao = db.movieDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAll() = runBlocking {
        //arrange

        //act
        genreDao.insertAll(*Constants.genres)

        //assert
    }

}
