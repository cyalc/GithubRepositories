package com.cyalc.abnamrogithubrepositories.data.networking

import org.koin.dsl.module
import retrofit2.Retrofit

val networkingModule = module {
    single { provideGithubApi(get()) }
}

fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build()

fun provideGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

