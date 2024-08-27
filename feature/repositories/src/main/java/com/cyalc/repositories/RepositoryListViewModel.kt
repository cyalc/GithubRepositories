package com.cyalc.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyalc.repositories.ui.RepositoryUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 10

class RepositoryListViewModel(
    syncRepositoriesUseCase: SyncRepositoriesUseCase,
    repository: RepositoryRepository,
) : ViewModel() {

    private val _repositories = MutableStateFlow<List<RepositoryUiModel>>(emptyList())
    val repositories: StateFlow<List<RepositoryUiModel>> = _repositories

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

private fun Repository.toUiModel(): RepositoryUiModel = RepositoryUiModel(
    id = id,
    name = name,
    ownerImageUrl = "ownerImageUrl",
    visibility = true,
    status = RepositoryUiModel.Status.PUBLIC
)
