package com.academy.bangkit.mygithubuser.data.local.room

import androidx.room.*
import com.academy.bangkit.mygithubuser.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * from users WHERE username = :username")
    fun getUser(username: String): Flow<List<UserEntity>>

    @Query("SELECT * from users ORDER BY username ASC")
    fun getUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun delete(id: Int)

}