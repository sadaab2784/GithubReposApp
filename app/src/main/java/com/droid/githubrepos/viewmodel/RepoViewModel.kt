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
    private val repository: GitHubRepository
) : ViewModel() {

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var currentPage = 1
    private val perPage = 10 // Number of items per page

    fun loadRepositories(username: String) {
        viewModelScope.launch {
            try {
                val newRepositories = repository.getRepositories(username, currentPage, perPage)

                val currentRepositories = _repositories.value ?: emptyList()
                _repositories.value = currentRepositories + newRepositories
                currentPage++

            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }
}