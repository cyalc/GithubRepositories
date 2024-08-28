package com.cyalc.repositories

data class Repo(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val htmlUrl: String?,
    val ownerAvatarUrl: String,
    val visibility: String,
    val isPrivate: Boolean,
) {
    val isPublic: Boolean
        get() = !isPrivate
}
