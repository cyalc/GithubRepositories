package com.cyalc.repositories

import com.cyalc.logging.Logger
import com.cyalc.repositories.datasource.local.RepositoryDao
import com.cyalc.repositories.datasource.remote.GithubApi
import com.cyalc.repositories.datasource.remote.GithubRepoApiModel
import com.cyalc.repositories.datasource.local.RepoDbModel
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
                buildRandomRepositoryModel(),
                buildRandomRepositoryModel(),
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
            buildRandomRepositoryModel(),
            buildRandomRepositoryModel()
        )
        coEvery { mockGithubApi.fetchRepositories(1, 10) } returns repositoryModels

        // When
        syncRepositoriesUseCase.execute()

        // Then
        val expectedEntities = listOf(
            RepoDbModel(
                id = 0,
                name = repositoryModels[0].name,
                ownerAvatarUrl = repositoryModels[0].ownerInfo.avatarUrl,
                visibility = repositoryModels[0].visibility,
                isPrivate = repositoryModels[0].isPrivate
            ),
            RepoDbModel(
                id = 1,
                name = repositoryModels[1].name,
                ownerAvatarUrl = repositoryModels[1].ownerInfo.avatarUrl,
                visibility = repositoryModels[1].visibility,
                isPrivate = repositoryModels[1].isPrivate
            )

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

private fun buildRandomRepositoryModel() = GithubRepoApiModel(
    id = (1..100).random().toLong(),
    name = "Repo${(1..100).random()}",
    ownerInfo = GithubRepoApiModel.OwnerInfo("http://some_url"),
    visibility = "public",
    isPrivate = false
)