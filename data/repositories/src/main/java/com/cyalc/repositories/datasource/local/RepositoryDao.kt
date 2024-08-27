package com.cyalc.repositories.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepoDbModel>)

    @Query("SELECT * FROM repositories")
    fun loadRepositories(): Flow<List<RepoDbModel>>
}