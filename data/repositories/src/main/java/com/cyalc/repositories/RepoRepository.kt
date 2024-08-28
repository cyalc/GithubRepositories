package com.cyalc.repositories

import com.cyalc.repositories.datasource.local.RepoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ReposRepositoryImpl(
    private val repoDao: RepoDao,
) : ReposRepository {
    override fun observeRepos() = repoDao.loadRepos()
        .map {
            it.map { repositoryDbModel -> repositoryDbModel.toDomainModel() }
        }
}

interface ReposRepository {
    fun observeRepos(): Flow<List<Repo>>
}