package com.droid.githubrepos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.githubrepos.model.Repository
import com.droid.githubrepos.retrofit.GitHubRepository
import com.droid.githubrepos.retrofit.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel(
    private val repository: GitHubRepository // Dependency injected via constructor
) : ViewModel() {

    // LiveData to hold the list of repositories
    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    // LiveData to hold error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Pagination variables
    private var currentPage = 1
    private val perPage = 10 // Number of items per page

    // Function to load repositories
    fun loadRepositories(username: String) {
        viewModelScope.launch {
            try {
                // Fetch repositories from the API
                val newRepositories = repository.getRepositories(username, currentPage, perPage)

                // Update the list of repositories
                val currentRepositories = _repositories.value ?: emptyList()
                _repositories.value = currentRepositories + newRepositories

                // Increment the page number for the next load
                currentPage++
            } catch (e: Exception) {
                // Handle errors and update the error message
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }
}