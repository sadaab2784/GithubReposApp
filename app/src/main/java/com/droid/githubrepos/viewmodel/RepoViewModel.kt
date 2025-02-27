package com.droid.githubrepos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.githubrepos.model.Repository
import com.droid.githubrepos.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class RepoViewModel : ViewModel() {

    // LiveData to hold the list of repositories
    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    // LiveData to hold error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // LiveData to track loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Pagination variables
    private var currentPage = 1
    private val perPage = 10 // Number of items per page

    // Fetch repositories for a specific GitHub user
    fun fetchRepositories(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getRepositories(username, currentPage, perPage)
                _repositories.value = response
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            }
        }
    }
}