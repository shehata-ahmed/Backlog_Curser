package com.ashehata.backlog_curser.data.local.dao

import androidx.room.*
import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.data.local.entity.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE status = :status")
    fun getTasksByStatus(status: TaskStatus): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE category = :category")
    fun getTasksByCategory(category: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE tags LIKE '%' || :tag || '%'")
    fun getTasksByTag(tag: String): Flow<List<Task>>

    @Query(
        """
        SELECT * FROM tasks
        WHERE title LIKE :pattern
           OR description LIKE :pattern
           OR tags LIKE :pattern
        ORDER BY CASE status WHEN 'DONE' THEN 1 ELSE 0 END, updatedAt DESC
        """
    )
    fun searchTasks(pattern: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT COUNT(*) FROM tasks WHERE status = :status")
    fun getTaskCountByStatus(status: TaskStatus): Flow<Int>

    @Query("UPDATE tasks SET status = :newStatus WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Long, newStatus: TaskStatus)

    @Query("DELETE FROM tasks")
    fun deleteAll()
} 