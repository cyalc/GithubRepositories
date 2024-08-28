package com.cyalc.repositories.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RepoDbModel::class],
    version = 6,
)
internal abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}