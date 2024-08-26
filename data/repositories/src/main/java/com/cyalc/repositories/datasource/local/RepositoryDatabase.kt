package com.cyalc.repositories.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepositoryDbModel::class], version = 1)
internal abstract class RepositoryDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}