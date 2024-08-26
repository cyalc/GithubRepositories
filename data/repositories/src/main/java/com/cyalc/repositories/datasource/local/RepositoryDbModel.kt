package com.cyalc.repositories.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class RepositoryDbModel(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
)