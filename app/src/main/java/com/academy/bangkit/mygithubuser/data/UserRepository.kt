package com.academy.bangkit.mygithubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.academy.bangkit.mygithubuser.data.local.entity.UserEntity
import com.academy.bangkit.mygithubuser.data.local.room.UserDao
import com.academy.bangkit.mygithubuser.data.network.response.UserResponse
import com.academy.bangkit.mygithubuser.data.network.retrofit.GithubApiService

class UserRepository private constructor(
    private val userDao: UserDao,
) {

    fun allUsers(): LiveData<List<UserEntity>> = userDao.getUsers().asLiveData()

    suspend fun insertUser(user: UserEntity) {
        userDao.insert(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        userDao.delete(user)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userDao: UserDao
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(userDao)
        }.also { instance = it }
    }

}