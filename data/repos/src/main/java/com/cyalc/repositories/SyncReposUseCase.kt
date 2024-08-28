package com.cyalc.repositories

import com.cyalc.logging.Logger
import com.cyalc.repositories.datasource.local.RepoDao
import com.cyalc.repositories.datasource.remote.GithubApi
import retrofit2.HttpException

internal class SyncReposUseCaseImpl(
    private val githubApi: GithubApi,
    private val repoDao: RepoDao,
    private val logger: Logger,
) : SyncReposUseCase {
    override suspend fun execute(page: Int, size: Int): Result<Unit> = try {
        val repositories = githubApi.fetchRepositories(page, size)
            .map { repositoryModel -> repositoryModel.toDbModel() }
        repoDao.insertRepos(repositories)
        Result.success(Unit)
    } catch (httpException: HttpException) {
        logger.e(httpException)
        Result.failure(httpException)
    } catch (exception: Exception) {
        logger.e(exception)
        Result.failure(exception)
    }
}

interface SyncReposUseCase {
    suspend fun execute(page: Int, size: Int): Result<Unit>
}