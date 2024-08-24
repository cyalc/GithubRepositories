package com.cyalc.abnamrogithubrepositories.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cyalc.abnamrogithubrepositories.data.database.models.RepositoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repositoryentity")
    fun loadRepositories(): Flow<List<RepositoryEntity>>
}