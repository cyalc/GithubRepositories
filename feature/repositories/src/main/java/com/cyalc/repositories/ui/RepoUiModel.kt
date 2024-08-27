package com.cyalc.repositories.ui

import androidx.compose.runtime.Immutable

@Immutable
data class RepoUiModel(
    val id: Long,
    val name: String,
    val ownerAvatarUrl: String,
    val visibility: Boolean,
    val status: Status,
) {
    enum class Status { PRIVATE, PUBLIC }
}
