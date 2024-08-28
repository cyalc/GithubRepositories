package com.cyalc.repositories.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query

internal interface GithubApi {
    @GET("users/abnamrocoesd/repos")
    suspend fun fetchRepositories(
        @Query("page") page: Int,
        @Query("per_page") size: Int,
    ): List<RepoApiModel>
}