package com.cyalc.repositories.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
internal data class RepoDbModel(
    @PrimaryKey val id: Long,
    val name: String,
    val ownerAvatarUrl: String,
    val visibility: String,
    val isPrivate: Boolean,
)
