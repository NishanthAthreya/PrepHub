package com.example.prephub.screens.quizdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.example.prephub.data.QuizzesRepo
import com.example.prephub.data.entity.Quiz

/**
 * Quiz details view model.
 */
class QuizDetailsViewModel(private val quizzesRepo: QuizzesRepo) {

    /**
     * Returns list of quizzes.
     */
    @Composable
    fun quiz(): State<List<Quiz>> = quizzesRepo.quizzes.collectAsState(initial = listOf())
}