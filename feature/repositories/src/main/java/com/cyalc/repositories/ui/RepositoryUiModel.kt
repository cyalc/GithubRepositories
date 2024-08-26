package com.cyalc.repositories.ui

import androidx.compose.runtime.Immutable

@Immutable
data class RepositoryUiModel(
    val id: String,
    val name: String,
    val ownerImageUrl: String,
    val visibility: Boolean,
    val status: Status,
) {
    enum class Status { PRIVATE, PUBLIC }
}
