package com.cyalc.repositories.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyalc.repositories.ReposRepository
import com.cyalc.repositories.ui.RepoUiModel
import com.cyalc.repositories.ui.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepoDetailViewModel(
    private val repository: ReposRepository,
) : ViewModel() {

    private val _repo = MutableStateFlow<RepoUiModel?>(null)
    val repo: StateFlow<RepoUiModel?> = _repo

    fun loadRepo(id: Long) {
        viewModelScope.launch {
            repository.observeRepo(id).collect {
                _repo.value = it.toUiModel()
            }
        }
    }
}