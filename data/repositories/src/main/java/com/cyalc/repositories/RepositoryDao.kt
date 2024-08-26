package com.cyalc.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cyalc.repositories.models.RepositoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repositoryentity")
    fun loadRepositories(): Flow<List<RepositoryEntity>>
}