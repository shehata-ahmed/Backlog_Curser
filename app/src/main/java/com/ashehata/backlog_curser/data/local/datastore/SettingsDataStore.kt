package com.ashehata.backlog_curser.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val AUTO_ARCHIVE_DAYS = intPreferencesKey("auto_archive_days")
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DARK_THEME] ?: false
        }

    val isNotificationsEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: false
        }

    /**
     * Number of days after a task is marked Done before it is automatically
     * archived. A value of 0 disables auto-archive (completed tasks stay on the
     * board until manually archived). Default: 30 days.
     */
    val autoArchiveDays: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.AUTO_ARCHIVE_DAYS] ?: DEFAULT_AUTO_ARCHIVE_DAYS
        }

    suspend fun setDarkTheme(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = enabled
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    /**
     * Set the auto-archive window. Must be 0..365.
     * 0 disables auto-archive.
     */
    suspend fun setAutoArchiveDays(days: Int) {
        require(days in 0..MAX_AUTO_ARCHIVE_DAYS) {
            "auto_archive_days must be between 0 and $MAX_AUTO_ARCHIVE_DAYS"
        }
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTO_ARCHIVE_DAYS] = days
        }
    }

    companion object {
        const val DEFAULT_AUTO_ARCHIVE_DAYS = 30
        const val MAX_AUTO_ARCHIVE_DAYS = 365
    }
}
