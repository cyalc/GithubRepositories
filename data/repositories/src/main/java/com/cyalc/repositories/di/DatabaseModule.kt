package com.cyalc.repositories.di

import android.content.Context
import androidx.room.Room
import com.cyalc.repositories.datasource.local.RepositoryDao
import com.cyalc.repositories.datasource.local.RepositoryDatabase

internal fun provideDatabase(context: Context): RepositoryDatabase = Room
    .databaseBuilder(
        context = context,
        klass = RepositoryDatabase::class.java,
        name = "repositories-database"
    )
    .build()

internal fun provideRepositoryDao(database: RepositoryDatabase): RepositoryDao =
    database.repositoryDao()