package com.cyalc.repositories.ui

import com.cyalc.repositories.Repository

// Updated conversion function from Domain Model to UI Model
fun Repository.toUiModel(): RepoUiModel {
    return RepoUiModel(
        id = this.id,
        name = this.name,
        ownerAvatarUrl = this.ownerAvatarUrl,
        visibility = this.isPublic,
        status = if (this.isPublic) RepoUiModel.Status.PUBLIC else RepoUiModel.Status.PRIVATE
    )
}
