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
     * Bulk-duplicate a list of tasks. Each clone gets a fresh ID, the title is
     * prefixed with `(Copy)`, and the status is reset to `TO_DO` regardless of
     * the source task's status.
     *
     * Useful for "duplicate" actions on multi-select and for the templating
     * flow described in the docs.
     *
     * @param taskIds the IDs of the tasks to duplicate
     * @return the IDs of the newly-created cloned tasks, in the same order as
     *   the input. IDs that didn't resolve to a task are silently skipped.
     */
    suspend fun duplicateTasks(taskIds: List<Long>): List<Long>

    suspend fun createTask(task: Task)
    suspend fun updateTaskStatus(taskId: Long, newStatus: TaskStatus)
    suspend fun deleteAll()
}