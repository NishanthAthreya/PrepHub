package com.example.prephub.screens.main

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.prephub.screens.BaseFragment
import com.example.prephub.ui.theme.PrepHubTheme
import com.example.prephub.R
import com.example.prephub.data.QuizzesDatabase
import com.example.prephub.data.QuizzesRepo
import com.example.prephub.navigation.NavCoordinator
import kotlinx.coroutines.launch

/**
 * Main fragment, which represents initial screen.
 */
class MainFragment : BaseFragment() {
    override fun view(context: Context) = ComposeView(context).apply {
        setContent {
            MainScreen(context)
        }
        layoutParams = ViewGroup.LayoutParams(
            MATCH_PARENT,
            MATCH_PARENT
        )
    }
}

@Composable
private fun Fragment.MainScreen(context: Context) = PrepHubTheme {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Open))
    val searchState = remember{ mutableStateOf(false) }
    val inputTextState = remember{ mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val viewModel = MainViewModel(QuizzesRepo(QuizzesDatabase.getDatabase(context = context).quizzesDao()))

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    if (!searchState.value) {
                        Text("My hub")
                    } else {
                        TextField(
                            modifier = Modifier
                                .background(color = Color.Transparent)
                                .focusRequester(
                                    focusRequester
                                ),
                            value = inputTextState.value,
                            onValueChange = {
                                inputTextState.value = it
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        DisposableEffect(Unit) {
                            focusRequester.requestFocus()
                            onDispose { }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.run {
                                if(isOpen) close() else open()
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = "menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { searchState.value = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "search"
                        )
                    }
                },
                backgroundColor = Color.Transparent
            )
        },
        drawerContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = "Folders",
                    style = MaterialTheme.typography.h6
                )
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = {

                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "edit"
                    )
                }
            }
            Text(
                modifier = Modifier.padding(24 .dp),
                text = "All"
            )
            viewModel.quizzes().value.forEach{
                Text(
                    modifier = Modifier.padding(24 .dp),
                    text = it.folder
                )
            }
        },
        floatingActionButton = {
            Button(onClick = {
                NavCoordinator().navigateToQuizFragment(
                    this,
                    Bundle()
                )
            }){
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "add"
                )
            }
        },
        content = {
            viewModel.quizzes().value.forEach {
                Card(modifier = Modifier.fillMaxWidth()
                    .padding(24 .dp),
                    elevation = 4.dp
                ) {
                    Text(
                        modifier = Modifier.padding(8 .dp)
                            .clickable {
                                NavCoordinator().navigateToQuizDetailsFragment(
                                    this,
                                    Bundle()
                                )
                            },
                        text = it.name
                    )
                }
            }
        }
    )
}