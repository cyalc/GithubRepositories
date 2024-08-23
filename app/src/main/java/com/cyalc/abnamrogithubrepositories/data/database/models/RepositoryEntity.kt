package com.cyalc.abnamrogithubrepositories.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepositoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
)