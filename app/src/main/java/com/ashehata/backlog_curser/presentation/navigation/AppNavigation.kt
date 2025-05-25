package com.ashehata.backlog_curser.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashehata.backlog_curser.presentation.screens.kanban.KanbanScreen
import com.ashehata.backlog_curser.presentation.screens.progress.ProgressScreen
import com.ashehata.backlog_curser.presentation.screens.settings.SettingsScreen
import com.ashehata.backlog_curser.presentation.screens.add_task.AddTaskScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Kanban.route
    ) {
        composable(Screen.Kanban.route) {
            KanbanScreen(navController = navController)
        }
        composable(Screen.Progress.route) {
            ProgressScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(Screen.AddTask.route) {
            AddTaskScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
} 