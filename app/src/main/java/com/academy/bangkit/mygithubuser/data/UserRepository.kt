package com.academy.bangkit.mygithubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.academy.bangkit.mygithubuser.data.local.entity.UserEntity
import com.academy.bangkit.mygithubuser.data.local.room.UserDao

class UserRepository private constructor(
    private val userDao: UserDao,
) {
    fun allUsers(): LiveData<List<UserEntity>> = userDao.getUsers().asLiveData()
    fun retrieveUser(username: String): LiveData<List<UserEntity>> =
        userDao.getUser(username).asLiveData()

    suspend fun insertUser(user: UserEntity) {
        userDao.insert(user)
    }

    suspend fun deleteUser(id: Int) {
        userDao.delete(id)
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