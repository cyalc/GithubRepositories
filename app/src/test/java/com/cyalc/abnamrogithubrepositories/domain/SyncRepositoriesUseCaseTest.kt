package com.cyalc.abnamrogithubrepositories.domain

import com.cyalc.abnamrogithubrepositories.data.database.RepositoryDao
import com.cyalc.abnamrogithubrepositories.data.database.models.RepositoryEntity
import com.cyalc.abnamrogithubrepositories.data.networking.GithubApi
import com.cyalc.abnamrogithubrepositories.data.networking.models.RepositoryApiModel
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
    private val syncRepositoriesUseCase = SyncRepositoriesUseCase(mockGithubApi, mockRepositoryDao)

    @Test
    fun `execute should return success when API call and database insertion succeed`() = runBlocking {
        // Given
        val repositoryModels = listOf(
            RepositoryApiModel("1", "Repo1"),
            RepositoryApiModel("2", "Repo2"),

        )
        coEvery { mockGithubApi.fetchRepositories(1, 10) } returns repositoryModels

        // When
        val result = syncRepositoriesUseCase.execute()

        // Then
        assertTrue(result.isSuccess)
        coVerify { mockRepositoryDao.insertRepositories(any()) }
    }

    @Test
    fun `execute should map repository models to entities correctly`() = runBlocking {
        // Given
        val repositoryModels = listOf(
            RepositoryApiModel("1", "Repo1"),
            RepositoryApiModel("2", "Repo2"),
        )
        coEvery { mockGithubApi.fetchRepositories(1, 10) } returns repositoryModels

        // When
        syncRepositoriesUseCase.execute()

        // Then
        val expectedEntities = listOf(
            RepositoryEntity("1", "Repo1"),
            RepositoryEntity("2", "Repo2")
        )
        coVerify { mockRepositoryDao.insertRepositories(expectedEntities) }
    }

    @Test
    fun `execute should return failure when API call throws HttpException`() = runBlocking {
        // Given
        coEvery { mockGithubApi.fetchRepositories(1, 10) } throws mockk<HttpException>()

        // When
        val result = syncRepositoriesUseCase.execute()

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
        val result = syncRepositoriesUseCase.execute()

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is RuntimeException)
        coVerify(exactly = 0) { mockRepositoryDao.insertRepositories(any()) }
    }
}