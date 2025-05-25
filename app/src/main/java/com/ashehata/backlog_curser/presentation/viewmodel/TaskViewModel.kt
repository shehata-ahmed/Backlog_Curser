package com.ashehata.backlog_curser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashehata.backlog_curser.data.local.entity.Priority
import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.data.local.entity.TaskStatus
import com.ashehata.backlog_curser.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.getTasksByStatus(TaskStatus.TODO),
                repository.getTasksByStatus(TaskStatus.IN_PROGRESS),
                repository.getTasksByStatus(TaskStatus.DONE)
            ) { todo, inProgress, done ->
                TaskUiState(
                    todoTasks = todo,
                    inProgressTasks = inProgress,
                    doneTasks = done
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun createTask(
        title: String,
        description: String,
        priority: Priority,
        dueDate: Date?,
        tags: List<String>,
        category: String
    ) {
        viewModelScope.launch {
            val task = Task(
                title = title,
                description = description,
                priority = priority,
                dueDate = dueDate,
                tags = tags,
                status = TaskStatus.TODO,
                category = category
            )
            repository.insertTask(task)
        }
    }

    fun updateTaskStatus(task: Task, newStatus: TaskStatus) {
        viewModelScope.launch {
            repository.updateTask(task.copy(status = newStatus))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }
}

data class TaskUiState(
    val todoTasks: List<Task> = emptyList(),
    val inProgressTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 