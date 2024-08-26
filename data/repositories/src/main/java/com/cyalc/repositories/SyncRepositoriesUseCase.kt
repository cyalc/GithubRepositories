package com.cyalc.repositories

import com.cyalc.logging.Logger
import com.cyalc.repositories.models.RepositoryEntity
import retrofit2.HttpException

internal class SyncRepositoriesUseCaseImpl(
    private val githubApi: GithubApi,
    private val repositoryDao: RepositoryDao,
    private val logger: Logger,
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
        logger.e(httpException)
        Result.failure(httpException)
    } catch (exception: Exception) {
        logger.e(exception)
        Result.failure(exception)
    }
}

interface SyncRepositoriesUseCase {
    suspend fun execute(page: Int = 1, size: Int = 10): Result<Unit>
}