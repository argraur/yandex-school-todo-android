package dev.argraur.yandex.todo.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.argraur.yandex.todo.ui.R
import dev.argraur.yandex.todo.ui.elements.TodoCardList
import dev.argraur.yandex.todo.ui.navigation.TodoRoute
import dev.argraur.yandex.todo.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeScreenModel) {
    val uiState by viewModel.uiState.collectAsState()
    val todoItems by viewModel.todoItems.collectAsState()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    if (uiState is HomeScreenUiState.Loaded) {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = {
                        Column {
                            Text(
                                stringResource(R.string.home_title),
                                fontWeight = FontWeight.Bold,
                                style = Typography.titleLarge
                            )
                            AnimatedVisibility(topAppBarState.collapsedFraction < 0.5f) {
                                Text(
                                    stringResource(R.string.home_subtitle) + todoItems.count { it.done },
                                    style = Typography.bodyMedium,
                                    modifier = Modifier.alpha(0.5f).padding(top = 8.dp)
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.toggleVisibility() }) {
                            if ((uiState as HomeScreenUiState.Loaded).doneVisible) {
                                Icon(painterResource(R.drawable.ic_visibility), "Done are visible")
                            } else {
                                Icon(painterResource(R.drawable.ic_visibility_off), "Done are invisible")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.background),
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(start = 8.dp, top = 4.dp, end = 8.dp)
            ) {
                TodoCardList(
                    todoItems.filter {
                        if (!(uiState as HomeScreenUiState.Loaded).doneVisible)
                            !it.done
                        else true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    onRemoveTodoItem = {
                        viewModel.removeTodoItem(it)
                    },
                    onUpdateTodoItem = {
                        viewModel.updateTodoItem(it)
                    },
                    onTodoItemClick = {
                        navController.navigate("edit/${it.id}")
                    }
                )
                FloatingActionButton(
                    onClick = { navController.navigate(TodoRoute.New.route) },
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .padding(bottom = 24.dp, end = 24.dp)
                ) {
                    Icon(Icons.Default.Add, "Add todo")
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}