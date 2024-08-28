package com.cyalc.repositories

import com.cyalc.logging.Logger
import com.cyalc.repositories.datasource.local.RepoDao
import com.cyalc.repositories.datasource.remote.GithubApi
import com.cyalc.repositories.datasource.remote.RepoApiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.Headers
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class SyncReposUseCaseTest {

    private val mockGithubApi = mockk<GithubApi>()
    private val mockRepoDao = mockk<RepoDao>(relaxUnitFun = true)
    private val mockLogger = mockk<Logger>(relaxUnitFun = true)
    private val syncRepositoriesUseCase =
        SyncReposUseCaseImpl(mockGithubApi, mockRepoDao, mockLogger)

    @Test
    fun `execute should return success with hasMore true when Link header contains next`() = runBlocking {
        // Given
        val repositoryModels = listOf(buildRandomRepoApiModel(), buildRandomRepoApiModel())

        val headers = Headers.of("Link", "<https://api.github.com/user/repos?page=2>; rel=\"next\"")
        val response = Response.success(repositoryModels, headers)
        coEvery { mockGithubApi.fetchRepositories(1, 10) } returns response

        // When
        val result = syncRepositoriesUseCase.execute(1, 10)

        // Then
        assertTrue(result.isSuccess)
        val pagingInfo = result.getOrNull()
        assertTrue(pagingInfo?.hasMore == true)
        coVerify { mockRepoDao.insert(any()) }
    }

    @Test
    fun `execute should return success with hasMore false when Link header does not contain next`() = runBlocking {
        // Given
        val repositoryModels = listOf(buildRandomRepoApiModel(), buildRandomRepoApiModel())
        val headers = Headers.of("Link", "<https://api.github.com/user/repos?page=1>; rel=\"prev\"")
        val response = Response.success(repositoryModels, headers)
        coEvery { mockGithubApi.fetchRepositories(1, 10) } returns response

        // When
        val result = syncRepositoriesUseCase.execute(1, 10)

        // Then
        assertTrue(result.isSuccess)
        val pagingInfo = result.getOrNull()
        assertFalse(pagingInfo?.hasMore == true)
        coVerify { mockRepoDao.insert(any()) }
    }

    @Test
    fun `execute should return success with hasMore false when Link header is missing`() = runBlocking {
        // Given
        val repositoryModels = listOf(buildRandomRepoApiModel(), buildRandomRepoApiModel())
        val response = Response.success(repositoryModels)
        coEvery { mockGithubApi.fetchRepositories(1, 10) } returns response

        // When
        val result = syncRepositoriesUseCase.execute(1, 10)

        // Then
        assertTrue(result.isSuccess)
        val pagingInfo = result.getOrNull()
        assertFalse(pagingInfo?.hasMore == true)
        coVerify { mockRepoDao.insert(any()) }
    }

    @Test
    fun `execute should map repository models to entities correctly`() = runBlocking {
        // Given
        val apiModel1 = buildRandomRepoApiModel()
        val apiModel2 = buildRandomRepoApiModel()
        val repositoryModels = listOf(apiModel1, apiModel2)
        val response = Response.success(repositoryModels)
        coEvery { mockGithubApi.fetchRepositories(1, 10) } returns response

        // When
        syncRepositoriesUseCase.execute(1, 10)

        // Then
        val expectedEntities = listOf(apiModel1.toDbModel(), apiModel2.toDbModel())
        coVerify { mockRepoDao.insert(expectedEntities) }
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
        coVerify(exactly = 0) { mockRepoDao.insert(any()) }
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
        coVerify(exactly = 0) { mockRepoDao.insert(any()) }
    }
}

private fun buildRandomRepoApiModel() = RepoApiModel(
    id = (1..100).random().toLong(),
    name = "Repo${(1..100).random()}",
    ownerInfo = RepoApiModel.OwnerInfo("http://some_url"),
    visibility = "public",
    isPrivate = false,
    description = "Some description",
    htmlUrl = "http://some_url",
    fullName = "Some full name"
)