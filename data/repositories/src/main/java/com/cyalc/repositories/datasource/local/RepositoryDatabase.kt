package com.cyalc.repositories.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RepoDbModel::class],
    version = 5,
)
internal abstract class RepositoryDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}