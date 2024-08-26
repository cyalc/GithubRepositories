package com.cyalc.repositories

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cyalc.repositories.models.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1)
internal abstract class RepositoryDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}