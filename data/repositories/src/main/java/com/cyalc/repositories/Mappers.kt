package com.cyalc.repositories

import com.cyalc.repositories.datasource.local.RepoDbModel
import com.cyalc.repositories.datasource.remote.GithubRepoApiModel

internal fun GithubRepoApiModel.toDbModel(): RepoDbModel {
    return RepoDbModel(
        id = this.id,
        name = this.name,
        ownerAvatarUrl = this.ownerInfo.avatarUrl,
        visibility = this.visibility,
        isPrivate = this.isPrivate
    )
}

internal fun RepoDbModel.toDomainModel(): Repository {
    return Repository(
        id = this.id,
        name = this.name,
        ownerAvatarUrl = this.ownerAvatarUrl,
        visibility = this.visibility,
        isPrivate = this.isPrivate
    )
}
