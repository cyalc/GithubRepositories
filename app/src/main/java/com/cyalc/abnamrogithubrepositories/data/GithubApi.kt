package com.cyalc.abnamrogithubrepositories.data

import com.cyalc.abnamrogithubrepositories.data.models.RepositoryModel

interface GithubApi {
    suspend fun fetchRepositories(): List<RepositoryModel>
}