package dev.epool.themoviedb.db.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieGenreJoinDao {

    @Query(
        """
            SELECT
                id,
                name
            FROM genre
            INNER JOIN movie_genre_join ON genre.id=movie_genre_join.genre_id
            WHERE movie_genre_join.movie_id = :movieId
    """
    )
    fun getGenresForMovie(movieId: Int): LiveData<List<DbGenre>>

    @Query(
        """
            SELECT
                id,
                title,
                vote_average,
                release_date,
                overview,
                poster_path,
                backdrop_path,
                category,
                homepage
            FROM movie
            INNER JOIN movie_genre_join ON movie.id=movie_genre_join.movie_id
            WHERE movie_genre_join.genre_id = :genreId
    """
    )
    fun getMoviesForGenre(genreId: Int): LiveData<List<DbMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movieGenreJoins: DbMovieGenreJoin)

}