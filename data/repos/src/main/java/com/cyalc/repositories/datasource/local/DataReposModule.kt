package com.cyalc.repositories.datasource.local

import com.cyalc.logging.loggingModule
import com.cyalc.repositories.ReposRepository
import com.cyalc.repositories.ReposRepositoryImpl
import com.cyalc.repositories.SyncReposUseCase
import com.cyalc.repositories.SyncReposUseCaseImpl
import com.cyalc.repositories.datasource.remote.GithubApi
import com.cyalc.repositories.di.provideDatabase
import com.cyalc.repositories.di.provideGithubApi
import com.cyalc.repositories.di.provideReposDao
import com.cyalc.repositories.di.provideRetrofit
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit

val dataReposModule = module {
    single<RepoDatabase> { provideDatabase(androidApplication()) }
    single<Retrofit> { provideRetrofit() }
    single<GithubApi> { provideGithubApi(get()) }
    single<RepoDao> { provideReposDao(get()) }
    single<SyncReposUseCase> { SyncReposUseCaseImpl(get(), get(), get()) }
    single<ReposRepository> { ReposRepositoryImpl(get()) }
    includes(loggingModule)
}
