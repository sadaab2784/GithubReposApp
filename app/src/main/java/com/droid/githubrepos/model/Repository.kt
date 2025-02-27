package com.droid.githubrepos.model

data class Repository(
    val name: String,
    val description: String?,
    val language: String?,
    val stargazers_count: Int,
    val forks_count: Int
)
