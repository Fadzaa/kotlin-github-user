package com.example.github_api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_api.model.remote.ApiService

class MainViewModelFactory(private val apiService: ApiService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}