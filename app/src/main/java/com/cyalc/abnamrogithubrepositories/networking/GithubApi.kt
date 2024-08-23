package com.cyalc.abnamrogithubrepositories.networking

import com.cyalc.abnamrogithubrepositories.networking.models.RepositoryApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("users/abnamrocoesd/repos?page=1&per_page=10")
    suspend fun fetchRepositories(
        @Query("page") page: Int,
        @Query("per_page") size: Int,
    ): List<RepositoryApiModel>
}