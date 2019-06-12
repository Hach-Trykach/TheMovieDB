package dev.epool.themoviedb.db.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? = timestamp?.let { Date(it) }

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? = date?.time

}