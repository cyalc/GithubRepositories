package com.cyalc.abnamrogithubrepositories.domain

import com.cyalc.abnamrogithubrepositories.networking.GithubApi
import com.cyalc.abnamrogithubrepositories.data.GithubDb
import com.cyalc.abnamrogithubrepositories.data.models.RepositoryDbModel
import retrofit2.HttpException

class FetchAndSaveGithubRepositoryList(
    private val githubApi: GithubApi,
    private val githubDb: GithubDb,
) {
    suspend fun execute(): Result<Unit> = try {
        val repositories = githubApi.fetchRepositories(
            page = 1,
            size = 10,
        ).map { repositoryModel ->
            RepositoryDbModel(repositoryModel.id)
        }
        githubDb.saveRepositories(repositories)
        Result.success(Unit)
    } catch (httpException: HttpException) {
        Result.failure(httpException)
    }
}