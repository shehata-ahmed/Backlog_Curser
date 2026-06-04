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
     * Reactive list of tasks that are past their due date AND not yet `Done`.
     *
     * Comparison uses the device's current wall-clock time at emission, so the
     * stream re-evaluates membership when the underlying task list changes.
     * It does NOT auto-refresh on a timer — callers that need ticking behavior
     * should combine this with a periodic source.
     *
     * Results are sorted by due date ascending (oldest overdue first).
     *
     * @return a [Flow] of overdue tasks. Empty when nothing is overdue.
     */
    fun getOverdueTasks(): Flow<List<Task>>

    suspend fun createTask(task: Task)
    suspend fun updateTaskStatus(taskId: Long, newStatus: TaskStatus)
    suspend fun deleteAll()
}