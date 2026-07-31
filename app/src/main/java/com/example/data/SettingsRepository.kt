package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    private val tmdbApiKeyPref = stringPreferencesKey("tmdb_api_key")

    val tmdbApiKey: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[tmdbApiKeyPref]
    }

    suspend fun saveTmdbApiKey(apiKey: String) {
        context.dataStore.edit { preferences ->
            preferences[tmdbApiKeyPref] = apiKey
        }
    }
}
