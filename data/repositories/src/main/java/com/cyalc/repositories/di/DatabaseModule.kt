package com.cyalc.repositories.di

import android.content.Context
import androidx.room.Room
import com.cyalc.repositories.datasource.local.RepoDao
import com.cyalc.repositories.datasource.local.RepoDatabase

internal fun provideDatabase(context: Context): RepoDatabase = Room
    .databaseBuilder(
        context = context,
        klass = RepoDatabase::class.java,
        name = "repositories-database"
    )
    .fallbackToDestructiveMigration()
    .build()

internal fun provideReposDao(database: RepoDatabase): RepoDao =
    database.repositoryDao()