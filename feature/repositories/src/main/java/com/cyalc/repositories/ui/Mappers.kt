package com.cyalc.repositories.ui

import com.cyalc.repositories.Repo

fun Repo.toUiModel(): RepoUiModel = RepoUiModel(
    id = id,
    name = name,
    fullName = fullName,
    description = description ?: "",
    htmlUrl = htmlUrl,
    ownerAvatarUrl = ownerAvatarUrl,
    visibility = isPublic,
    status = if (isPublic) RepoUiModel.Status.PUBLIC else RepoUiModel.Status.PRIVATE
)
