package com.cyalc.abnamrogithubrepositories.data

import com.cyalc.abnamrogithubrepositories.data.models.RepositoryDbModel

interface GithubDb {
    suspend fun saveRepositories(repositories: List<RepositoryDbModel>)
}