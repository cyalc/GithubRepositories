package com.cyalc.abnamrogithubrepositories.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cyalc.abnamrogithubrepositories.data.database.models.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}