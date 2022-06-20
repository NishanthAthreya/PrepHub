package com.example.prephub.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prephub.data.entity.Quiz
import kotlinx.coroutines.flow.Flow

/**
 * Quizzes Dao.
 */
@Dao
interface QuizzesDao {

    @Query("SELECT * FROM quizzes")
    fun quizzes(): Flow<List<Quiz>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuiz(quiz: Quiz)
}