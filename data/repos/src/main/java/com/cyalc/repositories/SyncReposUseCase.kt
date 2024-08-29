package com.cyalc.repositories

import com.cyalc.logging.Logger
import com.cyalc.repositories.SyncReposUseCase.PagingInfo
import com.cyalc.repositories.datasource.local.RepoDao
import com.cyalc.repositories.datasource.remote.GithubApi
import retrofit2.HttpException

internal class SyncReposUseCaseImpl(
    private val githubApi: GithubApi,
    private val repoDao: RepoDao,
    private val logger: Logger,
) : SyncReposUseCase {

    override suspend fun execute(page: Int, size: Int): Result<PagingInfo> = try {
        val response = githubApi.fetchRepositories(page, size)

        if (response.isSuccessful) {
            val repositories = response.body() ?: emptyList()
            repoDao.insert(repositories.map { it.toDbModel() })

            val linkHeader = response.headers()["Link"]
            val hasMore = linkHeader?.contains("rel=\"next\"") ?: false
            Result.success(PagingInfo(hasMore))
        } else {
            Result.failure(HttpException(response))
        }
    } catch (httpException: HttpException) {
        logger.e(httpException)
        Result.failure(httpException)
    } catch (exception: Exception) {
        logger.e(exception)
        Result.failure(exception)
    }
}

interface SyncReposUseCase {
    suspend fun execute(page: Int, size: Int): Result<PagingInfo>

    data class PagingInfo(
        val hasMore: Boolean,
    )
}