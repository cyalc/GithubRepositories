package com.cyalc.abnamrogithubrepositories.data.networking

import retrofit2.Retrofit

class NetworkingModule {
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .build()
            .create(GithubApi::class.java)
    }
}