package com.example.prephub.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.prephub.R
import com.example.prephub.screens.addquiz.AddQuizFragment
import com.example.prephub.screens.quizdetails.QuizDetailsFragment

/**
 * Navigation coordinator.
 */
class NavCoordinator {

    fun navigateToQuizFragment(
        fragment1: Fragment,
        args: Bundle
    ) {
        val fragment2 = AddQuizFragment()
        fragment2.arguments = args
        val fragmentManager: FragmentManager = fragment1.parentFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            R.id.fragmentContainer,
            fragment2
        )
        transaction.addToBackStack("fragment2")
        transaction.commit()
    }

    fun navigateToQuizDetailsFragment(
        fragment1: Fragment,
        args: Bundle
    ) {
        val fragment2 = QuizDetailsFragment()
        fragment2.arguments = args
        val fragmentManager: FragmentManager = fragment1.parentFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            R.id.fragmentContainer,
            fragment2
        )
        transaction.addToBackStack("quizDetails")
        transaction.commit()
    }

    fun goBack(fragment: Fragment) {
        fragment.parentFragmentManager.popBackStack()
    }
}