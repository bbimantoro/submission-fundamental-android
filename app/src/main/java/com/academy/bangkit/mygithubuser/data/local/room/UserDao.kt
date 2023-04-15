package com.academy.bangkit.mygithubuser.data.local.room

import androidx.room.*
import com.academy.bangkit.mygithubuser.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * from users WHERE id = :id")
    fun getUser(id: Int): Flow<UserEntity>

    @Query("SELECT * from users ORDER BY username ASC")
    fun getUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

}