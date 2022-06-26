package com.example.prephub.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.prephub.data.dao.QuizzesDao
import com.example.prephub.data.entity.Quiz

/**
 * Quizzes database.
 */
@Database(entities = [Quiz::class], version = 1, exportSchema = false)
@TypeConverters(StringMapConverter::class)
abstract class QuizzesDatabase : RoomDatabase() {

    abstract fun quizzesDao(): QuizzesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: QuizzesDatabase? = null

        fun getDatabase(context: Context): QuizzesDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizzesDatabase::class.java,
                    "quizzes_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}