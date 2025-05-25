package com.ashehata.backlog_curser.presentation.screens.kanban

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.data.local.entity.TaskStatus
import com.ashehata.backlog_curser.presentation.components.TaskCard
import com.ashehata.backlog_curser.presentation.navigation.Screen
import com.ashehata.backlog_curser.presentation.viewmodel.KanbanViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanbanScreen(
    navController: NavController,
    viewModel: KanbanViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var draggedTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Backlog Curser") },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Progress.route) }) {
                        Icon(Icons.Default.ShowChart, contentDescription = "Progress")
                    }
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddTask.route) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // To Do Column
            Box(modifier = Modifier.weight(1f)) {
                KanbanColumn(
                    title = "To Do",
                    tasks = uiState.todoTasks,
                    onTaskDrop = { task ->
                        if (task.status != TaskStatus.TODO) {
                            viewModel.updateTaskStatus(task.id, TaskStatus.TODO)
                        }
                    },
                    draggedTask = draggedTask,
                    onDragStart = { draggedTask = it },
                    onDragEnd = { draggedTask = null }
                )
            }

            // In Progress Column
            Box(modifier = Modifier.weight(1f)) {
                KanbanColumn(
                    title = "In Progress",
                    tasks = uiState.inProgressTasks,
                    onTaskDrop = { task ->
                        if (task.status != TaskStatus.IN_PROGRESS) {
                            viewModel.updateTaskStatus(task.id, TaskStatus.IN_PROGRESS)
                        }
                    },
                    draggedTask = draggedTask,
                    onDragStart = { draggedTask = it },
                    onDragEnd = { draggedTask = null }
                )
            }

            // Done Column
            Box(modifier = Modifier.weight(1f)) {
                KanbanColumn(
                    title = "Done",
                    tasks = uiState.doneTasks,
                    onTaskDrop = { task ->
                        if (task.status != TaskStatus.DONE) {
                            viewModel.updateTaskStatus(task.id, TaskStatus.DONE)
                        }
                    },
                    draggedTask = draggedTask,
                    onDragStart = { draggedTask = it },
                    onDragEnd = { draggedTask = null }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun KanbanColumn(
    title: String,
    tasks: List<Task>,
    onTaskDrop: (Task) -> Unit,
    draggedTask: Task?,
    onDragStart: (Task) -> Unit,
    onDragEnd: () -> Unit
) {
    val columnBounds = remember { mutableStateOf<Rect?>(null) }
    var isOver by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                columnBounds.value = layoutCoordinates.boundsInWindow()
            }
            .background(
                if (isOver) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                else Color.Transparent
            )
            .pointerInput(draggedTask) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { /* no-op */ },
                    onDrag = { change, _ ->
                        val position = change.position
                        isOver = columnBounds.value?.contains(position) == true
                        change.consume()
                    },
                    onDragEnd = {
                        if (isOver && draggedTask != null) {
                            onTaskDrop(draggedTask)
                        }
                        isOver = false
                        onDragEnd()
                    },
                    onDragCancel = {
                        isOver = false
                        onDragEnd()
                    }
                )
            }
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    onTaskDrop = onTaskDrop,
                    isDragging = draggedTask?.id == task.id,
                    onDragStart = { onDragStart(task) },
                    onDragEnd = onDragEnd
                )
            }
        }
    }
}