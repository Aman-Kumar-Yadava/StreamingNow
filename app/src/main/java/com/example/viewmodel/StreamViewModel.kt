package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MediaItem
import com.example.data.NetworkModule
import com.example.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    data class Error(val message: String) : AuthState()
}

class StreamViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsRepository = SettingsRepository(application)
    private val api = NetworkModule.tmdbApi

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _trendingMovies = MutableStateFlow<List<MediaItem>>(emptyList())
    val trendingMovies: StateFlow<List<MediaItem>> = _trendingMovies.asStateFlow()
    
    private val _trendingTv = MutableStateFlow<List<MediaItem>>(emptyList())
    val trendingTv: StateFlow<List<MediaItem>> = _trendingTv.asStateFlow()
    
    private val _popularMovies = MutableStateFlow<List<MediaItem>>(emptyList())
    val popularMovies: StateFlow<List<MediaItem>> = _popularMovies.asStateFlow()
    
    private val _topRatedMovies = MutableStateFlow<List<MediaItem>>(emptyList())
    val topRatedMovies: StateFlow<List<MediaItem>> = _topRatedMovies.asStateFlow()

    private var currentApiKey: String? = null

    init {
        checkAuth()
    }

    private fun checkAuth() {
        viewModelScope.launch {
            val key = settingsRepository.tmdbApiKey.first()
            if (key.isNullOrBlank()) {
                _authState.value = AuthState.Unauthenticated
            } else {
                currentApiKey = key
                _authState.value = AuthState.Authenticated
                loadHomeData()
            }
        }
    }

    fun validateAndSaveApiKey(apiKey: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // Determine if it's a Bearer token or api_key query param. 
                // The prompt says "paste your TMDB API Key" but the v4 is Bearer, v3 can be api_key.
                // Let's assume it's a Bearer Read Access Token since that's standard now, or an API key that can be passed as Bearer. 
                // Actually, TMDB accepts v4 Bearer tokens in the Authorization header. Let's just format it as Bearer.
                val authHeader = "Bearer $apiKey"
                api.getConfiguration(authHeader)
                
                settingsRepository.saveTmdbApiKey(apiKey)
                currentApiKey = apiKey
                _authState.value = AuthState.Authenticated
                loadHomeData()
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Invalid API key or network error.")
            }
        }
    }
    
    fun resetAuthError() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Unauthenticated
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            currentApiKey?.let { key ->
                val authHeader = "Bearer $key"
                try {
                    _trendingMovies.value = api.getTrendingMovies(authHeader).results
                    _trendingTv.value = api.getTrendingTvShows(authHeader).results
                    _popularMovies.value = api.getPopularMovies(authHeader).results
                    _topRatedMovies.value = api.getTopRatedMovies(authHeader).results
                } catch (e: Exception) {
                    // Handle error (e.g. snackbar or state)
                }
            }
        }
    }
}
