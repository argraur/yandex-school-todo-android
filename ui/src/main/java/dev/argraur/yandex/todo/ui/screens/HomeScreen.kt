package dev.argraur.yandex.todo.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.ui.R
import dev.argraur.yandex.todo.ui.elements.ErrorSnackbar
import dev.argraur.yandex.todo.ui.elements.TodoCardList
import dev.argraur.yandex.todo.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeScreenModel, onNavigateToNew: () -> Unit, onNavigateToEdit: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val snackbarHostState = remember { SnackbarHostState() }
    val todoItems by uiState.todoItems.collectAsState(initial = listOf())

    ErrorSnackbar(
        snackbarHostState,
        uiState.isError,
        uiState.errorMsg,
        R.string.error_snackbar_action_label,
        onDismiss = { viewModel.dismissError() },
        onAction = { viewModel.refreshRepository() }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
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
                                modifier = Modifier
                                    .alpha(0.5f)
                                    .padding(top = 8.dp)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.toggleVisibility()
                    }) {
                        if (uiState.doneVisible) {
                            Icon(painterResource(R.drawable.ic_visibility),
                                stringResource(R.string.content_description_ic_visibility))
                        } else {
                            Icon(painterResource(R.drawable.ic_visibility_off),
                                stringResource(R.string.content_description_ic_visibility_off))
                        }
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.background),
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.refreshRepository() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 8.dp, top = 4.dp, end = 8.dp)
        ) {
            TodoCardList(
                todoItems.filter {
                    if (!uiState.doneVisible)
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
                    onNavigateToEdit(it.id)
                }
            )
            FloatingActionButton(
                onClick = { onNavigateToNew() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 24.dp, end = 24.dp)
            ) {
                Icon(Icons.Default.Add, stringResource(R.string.content_description_add_todo))
            }
        }
    }
}