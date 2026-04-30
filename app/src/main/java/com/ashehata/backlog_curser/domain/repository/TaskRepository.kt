package com.ashehata.backlog_curser.domain.repository

import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.data.local.entity.TaskStatus
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByStatus(status: TaskStatus): Flow<List<Task>>
    fun getTasksByCategory(category: String): Flow<List<Task>>
    fun getTasksByTag(tag: String): Flow<List<Task>>

    /**
     * Full-text search across task titles, descriptions, and tags.
     *
     * The match is case-insensitive and uses SQLite's LIKE operator with both
     * leading and trailing wildcards. An empty or blank query returns all tasks
     * (equivalent to [getAllTasks]).
     *
     * Results are ordered by status (Done last) and then by `updatedAt` desc.
     *
     * @param query the search term
     * @return a [Flow] that emits the matching tasks every time the underlying
     *   data changes
     */
    fun searchTasks(query: String): Flow<List<Task>>

    suspend fun getTaskById(taskId: Long): Task?
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getTaskCountByStatus(status: TaskStatus): Flow<Int>
    suspend fun createTask(task: Task)
    suspend fun updateTaskStatus(taskId: Long, newStatus: TaskStatus)
    suspend fun deleteAll()
}