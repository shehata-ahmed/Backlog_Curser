# Backlog - Personal Task Manager

A lightweight Android app for managing personal tasks and projects using a Kanban-style board. Built with modern Android development practices and optimized for minimal APK size.

## Features

- **Task Management**: Create, edit, and delete tasks with title, description, priority, due date, and tags
- **Kanban Board**: Organize tasks in a drag-and-drop board with To Do, In Progress, and Done columns
- **Local Notifications**: Get reminders for task due dates with snooze functionality
- **Task Filtering**: Filter tasks by tags or priority
- **Progress Tracking**: View task completion statistics and progress bars
- **Offline-First**: All data stored locally using Room database
- **Data Export**: Export tasks as JSON for backup
- **Theme Support**: Light and dark mode with Material 3 design
- **Recurring Tasks**: Schedule tasks to repeat daily, weekly, monthly, or on custom intervals — completed instances are auto-archived and the next occurrence is generated automatically
- **Quick Actions**: Long-press a task on the board to access quick edit, duplicate, archive, and delete actions without opening the detail view

## Technical Stack

- **UI**: Jetpack Compose with Material 3
- **Architecture**: MVVM with Clean Architecture
- **Local Storage**: Room Database
- **Dependency Injection**: Dagger Hilt
- **Asynchronous Operations**: Kotlin Coroutines
- **Navigation**: Jetpack Navigation Component
- **Background Tasks**: WorkManager for notifications

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run the app

## Testing

The app can be tested on any Android device or emulator running Android 5.0 (API 21) or higher. For best results, test on a device with Android 8.0 or higher.

### Testing Notifications

1. Create a task with a due date
2. Enable notifications in Settings
3. Wait for the notification to appear at the scheduled time
4. Test snooze functionality by swiping the notification

## APK Size Optimization

The app is optimized for minimal APK size:
- Uses R8/ProGuard for code shrinking
- Implements vector drawables
- Minimizes external dependencies
- Avoids heavy libraries (e.g., no TensorFlow Lite)

## Contributing

This is a personal project, but suggestions and improvements are welcome. Please feel free to submit issues or pull requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details. 