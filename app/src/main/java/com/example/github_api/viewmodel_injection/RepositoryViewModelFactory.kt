package com.example.github_api.viewmodel_injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_api.model.remote.ApiService
import com.example.github_api.viewmodel.RepositoryViewModel

class RepositoryViewModelFactory(private val username: String, private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepositoryViewModel(username, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}