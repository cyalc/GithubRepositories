package com.cyalc.abnamrogithubrepositories.data.networking

import com.cyalc.abnamrogithubrepositories.data.networking.models.RepositoryApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("users/abnamrocoesd/")
    suspend fun fetchRepositories(
        @Query("page") page: Int,
        @Query("per_page") size: Int,
    ): List<RepositoryApiModel>
}