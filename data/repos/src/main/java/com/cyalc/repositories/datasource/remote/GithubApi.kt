package com.cyalc.repositories.datasource.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GithubApi {
    @GET("users/cyalc/repos")
    suspend fun fetchRepositories(
        @Query("page") page: Int,
        @Query("per_page") size: Int,
    ): Response<List<RepoApiModel>>
}