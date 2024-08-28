package com.cyalc.repositories.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyalc.repositories.RepositoryRepository
import com.cyalc.repositories.SyncRepositoriesUseCase
import com.cyalc.repositories.ui.RepoUiModel
import com.cyalc.repositories.ui.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20


class RepoHomeViewModel(
    syncRepositoriesUseCase: SyncRepositoriesUseCase,
    repository: RepositoryRepository,
) : ViewModel() {

    private val _repositories = MutableStateFlow<List<RepoUiModel>>(emptyList())
    val repositories: StateFlow<List<RepoUiModel>> = _repositories

    init {
        viewModelScope.launch {
            repository.observeRepositories()
                .map { repositories ->
                    repositories.map { it.toUiModel() }
                }
                .collect {
                    _repositories.value = it
                }
        }

        viewModelScope.launch {
            syncRepositoriesUseCase.execute(page = 1, PAGE_SIZE)
        }
    }
}