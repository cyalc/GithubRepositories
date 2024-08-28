package com.cyalc.repositories.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: List<RepoDbModel>)

    @Query("SELECT * FROM repositories")
    fun loadAll(): Flow<List<RepoDbModel>>

    @Query("SELECT * FROM repositories WHERE id=:id")
    fun load(id: Long): Flow<RepoDbModel>

    @Query("DELETE FROM repositories")
    suspend fun deleteAll()
}
