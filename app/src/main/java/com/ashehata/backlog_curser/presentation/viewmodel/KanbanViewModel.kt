package com.ashehata.backlog_curser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.data.local.entity.TaskStatus
import com.ashehata.backlog_curser.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class KanbanUiState(
    val todoTasks: List<Task> = emptyList(),
    val inProgressTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList()
)

@HiltViewModel
class KanbanViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(KanbanUiState())
    val uiState: StateFlow<KanbanUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getAllTasks().collect { tasks ->
                _uiState.value = KanbanUiState(
                    todoTasks = tasks.filter { it.status == TaskStatus.TODO },
                    inProgressTasks = tasks.filter { it.status == TaskStatus.IN_PROGRESS },
                    doneTasks = tasks.filter { it.status == TaskStatus.DONE }
                )
            }
        }
    }

    fun updateTaskStatus(taskId: Long, newStatus: TaskStatus) {
        viewModelScope.launch {
            taskRepository.updateTaskStatus(taskId, newStatus)
        }
    }

    fun createTask(task: Task) {
        viewModelScope.launch {
            taskRepository.createTask(task)
        }
    }
} 