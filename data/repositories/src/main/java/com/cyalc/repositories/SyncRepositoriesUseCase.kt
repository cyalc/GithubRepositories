package com.cyalc.repositories

import android.net.http.HttpException
import com.cyalc.repositories.models.RepositoryEntity

internal class SyncRepositoriesUseCaseImpl(
    private val githubApi: GithubApi,
    private val repositoryDao: RepositoryDao,
) : SyncRepositoriesUseCase {
    override suspend fun execute(page: Int, size: Int): Result<Unit> = try {
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

interface SyncRepositoriesUseCase {
    suspend fun execute(page: Int = 1, size: Int = 10): Result<Unit>
}