package com.example.prephub.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.example.prephub.data.QuizzesRepo
import com.example.prephub.data.entity.Quiz

/**
 * Main view model.
 */
class MainViewModel(private val quizzesRepo: QuizzesRepo) {

    /**
     * Returns list of quizzes.
     */
    @Composable
    fun quizzes(): State<List<Quiz>> = quizzesRepo.quizzes.collectAsState(initial = listOf())
}