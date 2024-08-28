package com.cyalc.repositories.datasource.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GithubRepoApiModel(
    val id: Long,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val description: String?,
    @SerialName("html_url")
    val htmlUrl: String,
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
