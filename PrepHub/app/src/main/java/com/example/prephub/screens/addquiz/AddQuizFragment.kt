package com.example.prephub.screens.addquiz

import android.content.Context
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.example.prephub.R
import com.example.prephub.data.QuizzesDatabase
import com.example.prephub.data.QuizzesRepo
import com.example.prephub.data.entity.Quiz
import com.example.prephub.navigation.NavCoordinator
import com.example.prephub.screens.BaseFragment
import com.example.prephub.ui.theme.PrepHubTheme

/**
 * Add quiz fragment.
 */
class AddQuizFragment : BaseFragment() {
    override fun view(context: Context) = ComposeView(context).apply {
        setContent {
            AddQuizScreen(context)
        }
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}

@Composable
private fun Fragment.AddQuizScreen(context: Context) = PrepHubTheme {
    val scaffoldState = rememberScaffoldState()
    var quizTextState by remember { mutableStateOf("") }
    var folderTextState by remember { mutableStateOf("") }
    val viewModel = AddQuizViewModel(QuizzesRepo(QuizzesDatabase.getDatabase(context = context).quizzesDao()))
    val questionsAndAnswersStates = mutableMapOf<State<String>, State<String>>()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text("New Quiz")
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
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    value = quizTextState,
                    onValueChange = {
                        quizTextState = it
                    },
                    label = { Text("Enter quiz title") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    value = folderTextState,
                    onValueChange = {
                        folderTextState = it
                    },
                    label = { Text("Enter folder name") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                val options = listOf("1", "10", "100", "250", "1000")
                var expanded by remember { mutableStateOf(false) }
                var selectedOptionText by remember { mutableStateOf(options[0]) }
                var numQuestionsTextFieldSize by remember { mutableStateOf(Size.Zero)}

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            //This value is used to assign to the DropDown the same width
                            numQuestionsTextFieldSize = coordinates.size.toSize()
                    },
                    value = selectedOptionText,
                    onValueChange = { selectedOptionText = it },
                    label = { Text("Number of questions") },
                    trailingIcon = {
                        IconButton(onClick = {expanded = true}) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_drop_down),
                                contentDescription = "dropdown"
                            )
                        }
                    }
                )
                DropdownMenu(
                    modifier = Modifier
                        .width(with(LocalDensity.current){numQuestionsTextFieldSize.width.toDp()}),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        ) {
                            Text(text = selectionOption)
                        }
                    }
                }
                if (selectedOptionText.isDigitsOnly() && selectedOptionText.isNotEmpty()) {
                    LazyColumn {
                        questionsAndAnswersStates.clear()
                        if (selectedOptionText.isNotEmpty()) {
                            items(selectedOptionText.toInt()) {
                                val question = remember { mutableStateOf("") }
                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = Color.Transparent),
                                    value = question.value,
                                    onValueChange = {
                                        question.value = it
                                    },
                                    label = { Text("Enter question ${it + 1}") },
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent
                                    )
                                )
                                val answer = remember { mutableStateOf("") }
                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = Color.Transparent)
                                        .padding(bottom = 24.dp),
                                    value = answer.value,
                                    onValueChange = {
                                        answer.value = it
                                    },
                                    label = { Text("Enter answer ${it + 1}") },
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent
                                    )
                                )
                                questionsAndAnswersStates[question] = answer
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            Button(onClick = {
                val questionsAndAnswers = mutableMapOf<String, String>()
                questionsAndAnswersStates.forEach { (question, answer) ->
                    questionsAndAnswers[question.value] = answer.value
                }
                viewModel.addQuiz(
                    quiz = Quiz(
                        name = quizTextState,
                        folder = folderTextState,
                        questionsAndAnswers = questionsAndAnswers
                    )
                )
                NavCoordinator().goBack(this)
            }){
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "add"
                )
            }
        }
    )
}