package com.cyalc.abnamrogithubrepositories.data.database

import android.app.Application
import androidx.room.Room
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideRepositoryDao(get()) }
}

fun provideDatabase(application: Application): AppDatabase = Room
    .databaseBuilder(
        context = application,
        klass = AppDatabase::class.java,
        name = "repositories-database"
    )
    .build()

fun provideRepositoryDao(database: AppDatabase): RepositoryDao = database.repositoryDao()