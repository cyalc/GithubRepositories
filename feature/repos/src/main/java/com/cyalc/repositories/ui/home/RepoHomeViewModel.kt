package com.cyalc.repositories.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyalc.repositories.ReposRepository
import com.cyalc.repositories.SyncReposUseCase
import com.cyalc.repositories.ui.RepoUiModel
import com.cyalc.repositories.ui.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 10

class RepoHomeViewModel(
    private val syncRepositoriesUseCase: SyncReposUseCase,
    private val repository: ReposRepository,
) : ViewModel() {

    private val _repos = MutableStateFlow<List<RepoUiModel>>(emptyList())
    val repos: StateFlow<List<RepoUiModel>> = _repos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1
    private var hasMoreItems = true

    init {
        observeRepos()
        loadNextPage()
    }

    internal fun loadNextPage() {
        if (_isLoading.value || !hasMoreItems) return

        viewModelScope.launch {
            _isLoading.value = true

            val result = syncRepositoriesUseCase.execute(
                page = currentPage,
                size = PAGE_SIZE
            )

            result
                .onSuccess { pagingInfo ->
                    hasMoreItems = pagingInfo.hasMore
                    if (hasMoreItems) currentPage++
                }
                .onFailure { exception ->
                    // TODO: handle error
                    hasMoreItems = false
                }

            _isLoading.value = false
        }
    }

    private fun observeRepos() {
        viewModelScope.launch {
            repository.observeRepos()
                .map { repos ->
                    repos.map { it.toUiModel() }
                }
                .collect {
                    _repos.value = it
                }
        }
    }
}
