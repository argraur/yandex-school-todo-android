package dev.argraur.yandex.todo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.ui.R
import dev.argraur.yandex.todo.ui.elements.TodoCardList
import dev.argraur.yandex.todo.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val toast = Toast.makeText(LocalContext.current, "Hello!", Toast.LENGTH_LONG)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(stringResource(R.string.home_title), fontWeight = FontWeight.Bold, style = Typography.titleLarge)
                        Text("Выполнено - " + 1, style = Typography.bodyMedium)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // TODO: Change visibility upon click
                    }) {
                        Icon(painterResource(R.drawable.ic_visibility), "Toggle visibility")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 8.dp)) {
            TodoCardList(
                listOf(TodoItem("", "Hello")),
                modifier = Modifier.fillMaxWidth()
            )
            FloatingActionButton(
                onClick = { toast.show() },
                modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 24.dp, end = 24.dp)
            ) {
                Icon(Icons.Default.Add, "Add todo")
            }
        }
    }
}