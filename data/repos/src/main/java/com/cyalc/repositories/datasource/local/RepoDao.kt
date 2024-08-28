package com.cyalc.repositories.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoDbModel>)

    @Query("SELECT * FROM repositories")
    fun loadRepos(): Flow<List<RepoDbModel>>

    @Query("SELECT * FROM repositories WHERE id=:id")
    fun loadRepo(id: Long) : Flow<RepoDbModel>
}
