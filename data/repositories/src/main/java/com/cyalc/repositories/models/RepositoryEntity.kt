package com.cyalc.repositories.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class RepositoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
)