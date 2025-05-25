package com.ashehata.backlog_curser.presentation.screens.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ashehata.backlog_curser.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress") },
                navigationIcon = {
                    IconButton(onClick = navController::navigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ProgressCard(
                title = "Task Completion",
                totalTasks = uiState.todoTasks.size + uiState.inProgressTasks.size + uiState.doneTasks.size,
                completedTasks = uiState.doneTasks.size
            )

            Spacer(modifier = Modifier.height(16.dp))

            CategoryProgress(
                title = "To Do",
                count = uiState.todoTasks.size,
                total = uiState.todoTasks.size + uiState.inProgressTasks.size + uiState.doneTasks.size
            )

            Spacer(modifier = Modifier.height(8.dp))

            CategoryProgress(
                title = "In Progress",
                count = uiState.inProgressTasks.size,
                total = uiState.todoTasks.size + uiState.inProgressTasks.size + uiState.doneTasks.size
            )

            Spacer(modifier = Modifier.height(8.dp))

            CategoryProgress(
                title = "Done",
                count = uiState.doneTasks.size,
                total = uiState.todoTasks.size + uiState.inProgressTasks.size + uiState.doneTasks.size
            )
        }
    }
}

@Composable
fun ProgressCard(
    title: String,
    totalTasks: Int,
    completedTasks: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$completedTasks of $totalTasks tasks completed",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CategoryProgress(
    title: String,
    count: Int,
    total: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$count tasks",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
} 