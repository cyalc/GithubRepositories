package com.cyalc.abnamrogithubrepositories.domain

import com.cyalc.abnamrogithubrepositories.data.database.RepositoryDao
import com.cyalc.abnamrogithubrepositories.data.database.models.RepositoryEntity
import com.cyalc.abnamrogithubrepositories.data.networking.GithubApi
import retrofit2.HttpException

class SyncRepositoriesUseCase(
    private val githubApi: GithubApi,
    private val repositoryDao: RepositoryDao,
) {
    suspend fun execute(page: Int = 1, size: Int = 10): Result<Unit> = try {
        val repositories = githubApi.fetchRepositories(page, size)
            .map { repositoryModel ->
                RepositoryEntity(
                    id = repositoryModel.id,
                    name = repositoryModel.name
                )
            }
        repositoryDao.insertRepositories(repositories)
        Result.success(Unit)
    } catch (httpException: HttpException) {
        Result.failure(httpException)
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}