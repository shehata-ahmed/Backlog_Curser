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
    suspend fun createTask(task: Task)
    suspend fun updateTaskStatus(taskId: Long, newStatus: TaskStatus)
    suspend fun deleteAll()

    /**
     * Bulk-archive every task currently in `Done` status.
     *
     * Archived tasks are removed from the active board but retained in the
     * database (status set to `ARCHIVED`). Use [restoreArchivedTask] to
     * un-archive an individual task.
     *
     * @return the number of tasks that were archived
     */
    suspend fun archiveCompletedTasks(): Int

    /**
     * Restore a previously-archived task back to `Done` status, returning it
     * to the visible board. No-op if the task is not currently archived.
     *
     * @return `true` if the task was restored, `false` if it was not archived
     */
    suspend fun restoreArchivedTask(taskId: Long): Boolean
}