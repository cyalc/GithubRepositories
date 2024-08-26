package com.cyalc.repositories.di

import com.cyalc.repositories.datasource.remote.GithubApi
import org.koin.dsl.module
import retrofit2.Retrofit

val networkingModule = module {
    single { provideGithubApi(get()) }
}

internal fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build()

internal fun provideGithubApi(retrofit: Retrofit): GithubApi =
    retrofit.create(GithubApi::class.java)

