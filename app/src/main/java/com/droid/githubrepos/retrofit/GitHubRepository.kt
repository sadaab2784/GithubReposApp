package com.droid.githubrepos.retrofit

import com.droid.githubrepos.model.Repository
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val apiService: GitHubApiService) {

    suspend fun getRepositories(username: String, page: Int, perPage: Int): Result<List<Repository>> {
        return try {
            val response = apiService.getRepositories(username, page, perPage)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }
}