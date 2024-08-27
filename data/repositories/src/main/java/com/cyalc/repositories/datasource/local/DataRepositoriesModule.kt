package com.cyalc.repositories.datasource.local

import com.cyalc.logging.loggingModule
import com.cyalc.repositories.RepositoryRepository
import com.cyalc.repositories.RepositoryRepositoryImpl
import com.cyalc.repositories.SyncRepositoriesUseCase
import com.cyalc.repositories.SyncRepositoriesUseCaseImpl
import com.cyalc.repositories.datasource.remote.GithubApi
import com.cyalc.repositories.di.provideDatabase
import com.cyalc.repositories.di.provideGithubApi
import com.cyalc.repositories.di.provideRepositoryDao
import com.cyalc.repositories.di.provideRetrofit
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit

val dataRepositoriesModule = module {
    single<RepositoryDatabase> { provideDatabase(androidApplication()) }
    single<Retrofit> { provideRetrofit() }
    single<GithubApi> { provideGithubApi(get()) }
    single<RepositoryDao> { provideRepositoryDao(get()) }
    single<SyncRepositoriesUseCase> { SyncRepositoriesUseCaseImpl(get(), get(), get()) }
    single<RepositoryRepository> { RepositoryRepositoryImpl(get()) }
    includes(loggingModule)
}
