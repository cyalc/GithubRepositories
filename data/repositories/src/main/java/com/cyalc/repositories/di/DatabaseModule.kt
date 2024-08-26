package com.cyalc.repositories.di

import android.app.Application
import androidx.room.Room
import com.cyalc.repositories.RepositoryDao
import com.cyalc.repositories.RepositoryDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideRepositoryDao(get()) }
}

internal fun provideDatabase(application: Application): RepositoryDatabase = Room
    .databaseBuilder(
        context = application,
        klass = RepositoryDatabase::class.java,
        name = "repositories-database"
    )
    .build()

internal fun provideRepositoryDao(database: RepositoryDatabase): RepositoryDao =
    database.repositoryDao()