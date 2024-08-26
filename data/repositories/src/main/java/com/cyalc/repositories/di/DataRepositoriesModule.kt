package com.cyalc.repositories.di

import com.cyalc.logging.loggingModule
import com.cyalc.repositories.SyncRepositoriesUseCase
import com.cyalc.repositories.SyncRepositoriesUseCaseImpl
import com.cyalc.repositories.datasource.local.RepositoryDao
import com.cyalc.repositories.datasource.remote.GithubApi
import org.koin.dsl.module
import retrofit2.Retrofit

val dataRepositoriesModule = module {
    single<GithubApi> { provideGithubApi(get()) }
    single<RepositoryDao> { provideRepositoryDao(get()) }
    single<Retrofit> { provideRetrofit() }
    single<SyncRepositoriesUseCase> { SyncRepositoriesUseCaseImpl(get(), get(), get()) }
    loggingModule
}
