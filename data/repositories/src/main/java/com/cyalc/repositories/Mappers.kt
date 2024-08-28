package com.cyalc.repositories

import com.cyalc.repositories.datasource.local.RepoDbModel
import com.cyalc.repositories.datasource.remote.GithubRepoApiModel

internal fun GithubRepoApiModel.toDbModel(): RepoDbModel = RepoDbModel(
    id = id,
    name = name,
    ownerAvatarUrl = ownerInfo.avatarUrl,
    visibility = visibility,
    isPrivate = isPrivate,
    description = description,
    htmlUrl = htmlUrl,
    fullName = fullName
)

internal fun RepoDbModel.toDomainModel(): Repository = Repository(
    id = id,
    name = name,
    fullName = fullName,
    description = description,
    htmlUrl = htmlUrl,
    ownerAvatarUrl = ownerAvatarUrl,
    visibility = visibility,
    isPrivate = isPrivate
)
