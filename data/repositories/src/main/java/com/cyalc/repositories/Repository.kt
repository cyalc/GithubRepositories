package com.cyalc.repositories

data class Repository(
    val id: Long,
    val name: String,
    val ownerAvatarUrl: String,
    val visibility: String,
    val isPrivate: Boolean
) {
    val isPublic: Boolean
        get() = !isPrivate
}
