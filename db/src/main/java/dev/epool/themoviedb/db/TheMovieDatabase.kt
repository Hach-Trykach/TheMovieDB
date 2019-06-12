package dev.epool.themoviedb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.epool.themoviedb.db.converters.DateConverter
import dev.epool.themoviedb.db.converters.MovieCategoryConverter
import dev.epool.themoviedb.db.entities.*

@Database(
    version = 1,
    entities = [
        DbMovie::class,
        DbGenre::class,
        DbMovieGenreJoin::class
    ],
    exportSchema = false
)
@TypeConverters(MovieCategoryConverter::class, DateConverter::class)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun movieGenreJoinDao(): MovieGenreJoinDao

    companion object {

        @Volatile
        private var INSTANCE: TheMovieDatabase? = null

        fun getDatabase(context: Context): TheMovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TheMovieDatabase::class.java,
                    "TheMovieDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}