package com.example.prephub.screens.addquiz

import androidx.lifecycle.*
import com.example.prephub.data.QuizzesRepo
import com.example.prephub.data.entity.Quiz
import kotlinx.coroutines.launch

/**
 * Add quiz view model.
 */
class AddQuizViewModel(private val quizzesRepo: QuizzesRepo) : ViewModel() {

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun addQuiz(quiz: Quiz) = viewModelScope.launch {
        quizzesRepo.addQuiz(quiz)
    }
}