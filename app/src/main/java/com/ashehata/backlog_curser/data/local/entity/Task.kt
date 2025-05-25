package com.ashehata.backlog_curser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ashehata.backlog_curser.data.local.converter.DateConverter
import com.ashehata.backlog_curser.data.local.converter.ListConverter
import java.util.Date

@Entity(tableName = "tasks")
@TypeConverters(DateConverter::class, ListConverter::class)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val priority: Priority,
    val category: String = "",
    val dueDate: Date? = null,
    val tags: List<String> = emptyList(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE
}

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
} 