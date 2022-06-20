package com.example.prephub.data

import androidx.annotation.WorkerThread
import com.example.prephub.data.dao.QuizzesDao
import com.example.prephub.data.entity.Quiz
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class QuizzesRepo(private val quizzesDao: QuizzesDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val quizzes: Flow<List<Quiz>> = quizzesDao.quizzes()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addQuiz(quiz: Quiz) {
        quizzesDao.addQuiz(quiz)
    }
}