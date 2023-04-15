package com.academy.bangkit.mygithubuser.di

import android.content.Context
import com.academy.bangkit.mygithubuser.data.UserRepository
import com.academy.bangkit.mygithubuser.data.local.room.UserDatabase
import com.academy.bangkit.mygithubuser.data.network.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(dao)
    }
}