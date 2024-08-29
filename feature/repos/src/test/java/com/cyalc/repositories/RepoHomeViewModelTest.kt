package com.cyalc.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cyalc.connectivity.ConnectivityObserver
import com.cyalc.repositories.ui.home.RepoHomeViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val PAGE_SIZE = 10
private const val INITIAL_PAGE = 1

@ExperimentalCoroutinesApi
class RepoHomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var syncRepositoriesUseCase: SyncReposUseCase
    private lateinit var repository: ReposRepository
    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var viewModel: RepoHomeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        syncRepositoriesUseCase = mockk()
        repository = mockk {
            every { observeRepos() } returns flowOf(emptyList())
            coEvery { clearRepos() } just Runs
        }
        connectivityObserver = mockk()

        coEvery { syncRepositoriesUseCase.execute(any(), any()) } returns Result.success(
            SyncReposUseCase.PagingInfo(hasMore = true)
        )

        connectivityObserver = mockk {
            every { observe() } returns flowOf(ConnectivityObserver.Status.Available)
        }

        viewModel = RepoHomeViewModel(syncRepositoriesUseCase, repository, connectivityObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun loadNextPage_successfulLoad_updatesRepos() = runTest {
        val pagingInfo = SyncReposUseCase.PagingInfo(hasMore = true)
        coEvery { syncRepositoriesUseCase.execute(any(), any()) } returns Result.success(pagingInfo)

        viewModel.loadNextPage()

        coVerify { repository.clearRepos() }
        coVerify { syncRepositoriesUseCase.execute(INITIAL_PAGE, PAGE_SIZE) }
        assert(viewModel.repos.value.isEmpty())
        assert(!viewModel.isLoading.value)
        assert(!viewModel.isError.value)
    }

    @Test
    fun loadNextPage_failureLoad_setsError() = runTest {
        coEvery {
            syncRepositoriesUseCase.execute(
                any(),
                any()
            )
        } returns Result.failure(Exception())
        every { repository.observeRepos() } returns flowOf(emptyList())

        viewModel.loadNextPage()

        coVerify { repository.clearRepos() }
        coVerify { syncRepositoriesUseCase.execute(INITIAL_PAGE, PAGE_SIZE) }
        assert(viewModel.repos.value.isEmpty())
        assert(!viewModel.isLoading.value)
        assert(viewModel.isError.value)
    }

    @Test
    fun observeConnectivity_onlineAfterOffline_refreshesRepos() = runTest {
        every { connectivityObserver.observe() } returns flowOf(
            ConnectivityObserver.Status.Unavailable,
            ConnectivityObserver.Status.Available
        )
        every { repository.observeRepos() } returns flowOf(emptyList())
        coEvery { syncRepositoriesUseCase.execute(any(), any()) } returns Result.success(
            SyncReposUseCase.PagingInfo(
                hasMore = true
            )
        )

        viewModel.observeConnectivity()

        coVerify(exactly = 2) { syncRepositoriesUseCase.execute(INITIAL_PAGE, PAGE_SIZE) }
    }
}