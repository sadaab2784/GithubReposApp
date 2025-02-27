package com.droid.githubrepos.retrofit

import com.droid.githubrepos.model.Repository
import javax.inject.Inject

class GitHubRepository(
    private val githubService: GitHubApiService
) {
    suspend fun getRepositories(username: String, page: Int, perPage: Int): List<Repository> {
        return githubService.getRepositories(username, page, perPage)
    }
}