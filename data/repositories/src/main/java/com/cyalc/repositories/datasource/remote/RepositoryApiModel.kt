package com.cyalc.repositories.datasource.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GithubRepoApiModel(
    val id: Long,
    val name: String,
    @SerialName("owner")
    val ownerInfo: OwnerInfo,
    val visibility: String,
    @SerialName("private")
    val isPrivate: Boolean,
) {
    @Serializable
    data class OwnerInfo(
        @SerialName("avatar_url")
        val avatarUrl: String,
    )
}
