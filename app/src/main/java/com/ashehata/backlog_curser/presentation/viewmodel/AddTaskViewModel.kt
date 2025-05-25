package com.ashehata.backlog_curser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    fun createTask(task: Task) {
        viewModelScope.launch {
            taskRepository.createTask(task)
        }
    }
} 