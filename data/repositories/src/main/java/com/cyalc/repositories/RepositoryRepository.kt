package com.cyalc.repositories

import com.cyalc.repositories.datasource.local.RepositoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RepositoryRepositoryImpl(
    private val repositoryDao: RepositoryDao,
) : RepositoryRepository {
    override fun observeRepositories() = repositoryDao.loadRepositories()
        .map {
            it.map { repositoryDbModel ->
                Repository(
                    id = repositoryDbModel.id,
                    name = repositoryDbModel.name,
                )
            }
        }
}

interface RepositoryRepository {
    fun observeRepositories(): Flow<List<Repository>>
}