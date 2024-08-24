package com.cyalc.abnamrogithubrepositories.domain

import com.cyalc.abnamrogithubrepositories.data.database.RepositoryDao
import com.cyalc.abnamrogithubrepositories.data.networking.GithubApi
import com.cyalc.abnamrogithubrepositories.data.database.models.RepositoryEntity
import retrofit2.HttpException

class FetchAndSaveGithubRepositoryList(
    private val githubApi: GithubApi,
    private val repositoryDao: RepositoryDao,
) {
    suspend fun execute(): Result<Unit> = try {
        val repositories = githubApi.fetchRepositories(
            page = 1,
            size = 10,
        ).map { repositoryModel ->
            RepositoryEntity(repositoryModel.id, "name")
        }
        repositoryDao.insertRepositories(repositories)
        Result.success(Unit)
    } catch (httpException: HttpException) {
        Result.failure(httpException)
    }
}