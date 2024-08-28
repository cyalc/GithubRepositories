package com.cyalc.repositories.ui

import androidx.compose.runtime.Immutable

@Immutable
data class RepoUiModel(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String,
    val htmlUrl: String?,
    val ownerAvatarUrl: String,
    val visibility: Boolean,
    val status: Status,
) {
    enum class Status { PRIVATE, PUBLIC }
}
