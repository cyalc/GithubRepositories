package com.cyalc.repositories.di

import com.cyalc.repositories.datasource.remote.GithubApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private val json = Json {
    ignoreUnknownKeys = true
}

internal fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(
        json.asConverterFactory(
            "application/json; charset=UTF8".toMediaType()
        )
    )
    .build()

internal fun provideGithubApi(retrofit: Retrofit): GithubApi =
    retrofit.create(GithubApi::class.java)

private fun String.toMediaType(): MediaType {
    return MediaType.get(this)
}
