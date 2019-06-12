package dev.epool.themoviedb.db.converters

import androidx.room.TypeConverter
import dev.epool.themoviedb.db.entities.DbMovie

class MovieCategoryConverter {

    @TypeConverter
    fun toCategory(category: Int?): DbMovie.Category? =
        category?.let { DbMovie.Category.values()[it] }

    @TypeConverter
    fun toInteger(category: DbMovie.Category?): Int? = category?.ordinal

}