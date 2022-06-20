package com.example.prephub.screens.quizdetails

import android.content.Context
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
import com.example.prephub.R
import com.example.prephub.data.QuizzesDatabase
import com.example.prephub.data.QuizzesRepo
import com.example.prephub.navigation.NavCoordinator
import com.example.prephub.screens.BaseFragment
import com.example.prephub.ui.theme.PrepHubTheme

/**
 * Quiz details fragment.
 */
class QuizDetailsFragment: BaseFragment() {

    override fun view(context: Context) = ComposeView(context).apply {
        setContent {
            QuizDetailsScreen(context)
        }
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}

@Composable
private fun Fragment.QuizDetailsScreen(context: Context) = PrepHubTheme {
    val scaffoldState = rememberScaffoldState()
    var inputTextState by remember { mutableStateOf("") }
    val viewModel = QuizDetailsViewModel(QuizzesRepo(QuizzesDatabase.getDatabase(context = context).quizzesDao()))
    val questionsAndAnswers = mutableMapOf<String, String>()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(viewModel.quiz().value.firstOrNull()?.name.orEmpty())
                },
                navigationIcon = {
                    IconButton(onClick = {
                        NavCoordinator().goBack(this)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "menu"
                        )
                    }
                },
                backgroundColor = Color.Transparent
            )
        },
        content = {
            Column {
                viewModel.quiz().value.firstOrNull()?.questionsAndAnswers.orEmpty().forEach{
                    Text("Question: ${it.key}/nAnswer: ${it.value}")
                }
            }
        },
        floatingActionButton = {
            Button(onClick = {

            }){
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "add"
                )
            }
        }
    )
}