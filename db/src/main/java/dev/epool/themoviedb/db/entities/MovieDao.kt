package dev.epool.themoviedb.db.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun findById(movieId: Int): DbMovie?

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun findByIdLiveData(movieId: Int): LiveData<DbMovie>

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :query || '%' OR overview LIKE '%' ||  :query || '%' ORDER BY release_date DESC, title ASC")
    fun search(query: String): LiveData<List<DbMovie>>

    @Query("SELECT * FROM movie WHERE category = :category ORDER BY release_date DESC, title ASC")
    fun findByCategory(category: DbMovie.Category): LiveData<List<DbMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movies: DbMovie)

}