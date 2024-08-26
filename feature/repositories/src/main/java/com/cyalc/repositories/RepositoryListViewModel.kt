package com.cyalc.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 10

class RepositoryListViewModel(
    syncRepositoriesUseCase: SyncRepositoriesUseCase,
    repository: RepositoryRepository,
) : ViewModel() {

    init {
        repository.observeRepositories()

        viewModelScope.launch {
            syncRepositoriesUseCase.execute(page = 1, PAGE_SIZE)
        }
    }
}