package com.ashehata.backlog_curser.data.repository

import com.ashehata.backlog_curser.data.local.dao.TaskDao
import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.data.local.entity.TaskStatus
import com.ashehata.backlog_curser.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTasksByStatus(status: TaskStatus): Flow<List<Task>> =
        taskDao.getTasksByStatus(status)

    override fun getTasksByCategory(category: String): Flow<List<Task>> =
        taskDao.getTasksByCategory(category)

    override fun getTasksByTag(tag: String): Flow<List<Task>> =
        taskDao.getTasksByTag(tag)

    override fun searchTasks(query: String): Flow<List<Task>> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return taskDao.getAllTasks()
        return taskDao.searchTasks("%$trimmed%")
    }

    override suspend fun getTaskById(taskId: Long): Task? =
        taskDao.getTaskById(taskId)

    override suspend fun insertTask(task: Task): Long =
        taskDao.insertTask(task)

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task)

    override suspend fun deleteTask(task: Task) =
        taskDao.deleteTask(task)

    override fun getTaskCountByStatus(status: TaskStatus): Flow<Int> =
        taskDao.getTaskCountByStatus(status)

    override suspend fun createTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun updateTaskStatus(taskId: Long, newStatus: TaskStatus) =
        taskDao.updateTaskStatus(taskId, newStatus)

    override suspend fun deleteAll() {
        taskDao.deleteAll()
    }
} 