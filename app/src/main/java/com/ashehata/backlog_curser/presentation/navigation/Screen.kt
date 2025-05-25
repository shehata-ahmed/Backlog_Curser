package com.ashehata.backlog_curser.presentation.navigation

sealed class Screen(val route: String) {
    object Kanban : Screen("kanban")
    object AddTask : Screen("add_task")
    object Progress : Screen("progress")
    object Settings : Screen("settings")
} 