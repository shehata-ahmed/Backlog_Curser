package com.ashehata.backlog_curser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ashehata.backlog_curser.data.local.dao.TaskDao
import com.ashehata.backlog_curser.data.local.entity.Task
import com.ashehata.backlog_curser.data.local.util.Converters

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
} 