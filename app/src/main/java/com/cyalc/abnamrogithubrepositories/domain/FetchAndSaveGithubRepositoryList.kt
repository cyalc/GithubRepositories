package com.cyalc.abnamrogithubrepositories.domain

import com.cyalc.abnamrogithubrepositories.data.GithubApi
import com.cyalc.abnamrogithubrepositories.data.GithubDb
import com.cyalc.abnamrogithubrepositories.data.models.RepositoryDbModel

class FetchAndSaveGithubRepositoryList(
    private val githubApi: GithubApi,
    private val githubDb: GithubDb
) {
    suspend fun execute() {
        val repositories = githubApi.fetchRepositories().map { repositoryModel ->
            RepositoryDbModel(repositoryModel.id)
        }
        githubDb.saveRepositories(repositories)
    }
}