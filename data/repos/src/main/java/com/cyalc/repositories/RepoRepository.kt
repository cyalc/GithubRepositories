package com.cyalc.repositories

import com.cyalc.repositories.datasource.local.RepoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ReposRepositoryImpl(
    private val repoDao: RepoDao,
) : ReposRepository {
    override fun observeRepos() = repoDao.loadAll()
        .map {
            it.map { repositoryDbModel -> repositoryDbModel.toDomainModel() }
        }

    override fun observeRepo(id: Long): Flow<Repo> =
        repoDao.load(id).map { it.toDomainModel() }

    override suspend fun clearRepos() {
        repoDao.deleteAll()
    }
}

interface ReposRepository {
    fun observeRepos(): Flow<List<Repo>>

    fun observeRepo(id: Long): Flow<Repo>

    suspend fun clearRepos()
}