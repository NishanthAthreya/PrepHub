package com.example.prephub.screens.quizdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.example.prephub.data.QuizzesRepo
import com.example.prephub.data.entity.Quiz
import kotlinx.coroutines.flow.map

/**
 * Quiz details view model.
 */
class QuizDetailsViewModel(
    private val quizzesRepo: QuizzesRepo,
    private val quizName: String?
) {

    /**
     * Returns list of quizzes.
     */
    @Composable
    fun quiz(): State<Quiz?> = quizzesRepo.quizzes.map {
        it.first { quiz -> quiz.name == quizName }
    }.collectAsState(initial = null)
}