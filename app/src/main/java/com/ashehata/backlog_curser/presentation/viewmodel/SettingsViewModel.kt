package com.ashehata.backlog_curser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashehata.backlog_curser.data.local.datastore.SettingsDataStore
import com.ashehata.backlog_curser.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val isDarkTheme: Boolean = false,
    val isNotificationsEnabled: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataStore.isDarkTheme.collect { isDarkTheme ->
                _uiState.update { it.copy(isDarkTheme = isDarkTheme) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataStore.isNotificationsEnabled.collect { isNotificationsEnabled ->
                _uiState.update { it.copy(isNotificationsEnabled = isNotificationsEnabled) }
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataStore.setDarkTheme(!_uiState.value.isDarkTheme)
        }
    }

    fun toggleNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataStore.setNotificationsEnabled(!_uiState.value.isNotificationsEnabled)
        }
    }

    fun clearAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteAll()
        }
    }

} 