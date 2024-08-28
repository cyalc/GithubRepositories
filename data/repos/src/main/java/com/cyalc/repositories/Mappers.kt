package com.cyalc.repositories

import com.cyalc.repositories.datasource.local.RepoDbModel
import com.cyalc.repositories.datasource.remote.RepoApiModel

internal fun RepoApiModel.toDbModel(): RepoDbModel = RepoDbModel(
    id = id,
    name = name,
    ownerAvatarUrl = ownerInfo.avatarUrl,
    visibility = visibility,
    isPrivate = isPrivate,
    description = description,
    htmlUrl = htmlUrl,
    fullName = fullName
)

internal fun RepoDbModel.toDomainModel(): Repo = Repo(
    id = id,
    name = name,
    fullName = fullName,
    description = description,
    htmlUrl = htmlUrl,
    ownerAvatarUrl = ownerAvatarUrl,
    visibility = visibility,
    isPrivate = isPrivate
)
