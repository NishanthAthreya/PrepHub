package com.example.prephub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.prephub.screens.main.MainFragment

/**
 * Main activity.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(
            R.id.fragmentContainer,
            MainFragment()
        )
        transaction.commit()
        setContentView(R.layout.activity_main)
    }
}