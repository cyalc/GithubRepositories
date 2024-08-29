package com.cyalc.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cyalc.repositories.ui.RepoUiModel
import com.cyalc.repositories.ui.detail.RepoDetailViewModel
import com.cyalc.repositories.ui.toUiModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class RepoDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: ReposRepository
    private lateinit var viewModel: RepoDetailViewModel

    private val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = RepoDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadRepo_updatesRepo() = runTest {
        val repoId = 123L
        val repo = Repo(
            id = repoId,
            name = "name",
            description = "description",
            fullName = "fullName",
            htmlUrl = "htmlUrl",
            ownerAvatarUrl = "ownerAvatarUrl",
            isPrivate = false,
            visibility = "public",
        )
        val repoUiModel = repo.toUiModel()
        coEvery { repository.observeRepo(repoId) } returns flowOf(repo)

        viewModel.loadRepo(repoId)

        assert(viewModel.repo.first() == repoUiModel)
    }

}