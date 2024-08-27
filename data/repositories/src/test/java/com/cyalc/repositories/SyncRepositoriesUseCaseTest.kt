package com.cyalc.repositories

import com.cyalc.logging.Logger
import com.cyalc.repositories.datasource.local.RepositoryDao
import com.cyalc.repositories.datasource.remote.GithubApi
import com.cyalc.repositories.datasource.remote.GithubRepoApiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.HttpException

class SyncRepositoriesUseCaseTest {

    private val mockGithubApi = mockk<GithubApi>()
    private val mockRepositoryDao = mockk<RepositoryDao>(relaxUnitFun = true)
    private val mockLogger = mockk<Logger>(relaxUnitFun = true)
    private val syncRepositoriesUseCase =
        SyncRepositoriesUseCaseImpl(mockGithubApi, mockRepositoryDao, mockLogger)

    @Test
    fun `execute should return success when API call and database insertion succeed`() =
        runBlocking {
            // Given
            val repositoryModels = listOf(
                buildRandomRepoApiModel(),
                buildRandomRepoApiModel(),
            )
            coEvery { mockGithubApi.fetchRepositories(1, 10) } returns repositoryModels

            // When
            val result = syncRepositoriesUseCase.execute(1, 10)

            // Then
            assertTrue(result.isSuccess)
            coVerify { mockRepositoryDao.insertRepositories(any()) }
        }

    @Test
    fun `execute should map repository models to entities correctly`() = runBlocking {
        // Given
        val apiModel1 = buildRandomRepoApiModel()
        val apiModel2 = buildRandomRepoApiModel()
        val repositoryModels = listOf(apiModel1, apiModel2)
        coEvery { mockGithubApi.fetchRepositories(1, 10) } returns repositoryModels

        // When
        syncRepositoriesUseCase.execute(1, 10)

        // Then
        val expectedEntities = listOf(apiModel1.toDbModel(), apiModel2.toDbModel())
        coVerify { mockRepositoryDao.insertRepositories(expectedEntities) }
    }

    @Test
    fun `execute should return failure when API call throws HttpException`() = runBlocking {
        // Given
        coEvery { mockGithubApi.fetchRepositories(1, 10) } throws mockk<HttpException>()

        // When
        val result = syncRepositoriesUseCase.execute(1, 10)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is HttpException)
        coVerify(exactly = 0) { mockRepositoryDao.insertRepositories(any()) }
    }

    @Test
    fun `execute should return failure when any exception occurs`() = runBlocking {
        // Given
        coEvery { mockGithubApi.fetchRepositories(1, 10) } throws RuntimeException("Some error")

        // When
        val result = syncRepositoriesUseCase.execute(1, 10)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is RuntimeException)
        coVerify(exactly = 0) { mockRepositoryDao.insertRepositories(any()) }
    }
}

private fun buildRandomRepoApiModel() = GithubRepoApiModel(
    id = (1..100).random().toLong(),
    name = "Repo${(1..100).random()}",
    ownerInfo = GithubRepoApiModel.OwnerInfo("http://some_url"),
    visibility = "public",
    isPrivate = false
)