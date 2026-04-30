package com.ashehata.backlog_curser.domain.repository

import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.data.local.entity.TaskStatus
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByStatus(status: TaskStatus): Flow<List<Task>>
    fun getTasksByCategory(category: String): Flow<List<Task>>
    fun getTasksByTag(tag: String): Flow<List<Task>>
    suspend fun getTaskById(taskId: Long): Task?
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getTaskCountByStatus(status: TaskStatus): Flow<Int>

    /**
     * Reactive total count of tasks across all statuses (To Do, In Progress, Done).
     * Useful for the home-screen badge / app icon shortcuts.
     */
    fun getTotalTaskCount(): Flow<Int>

    suspend fun createTask(task: Task)
    suspend fun updateTaskStatus(taskId: Long, newStatus: TaskStatus)
    suspend fun deleteAll()
}