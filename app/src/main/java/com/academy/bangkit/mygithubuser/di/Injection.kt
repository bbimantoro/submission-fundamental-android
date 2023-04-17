package com.academy.bangkit.mygithubuser.di

import android.content.Context
import com.academy.bangkit.mygithubuser.data.UserRepository
import com.academy.bangkit.mygithubuser.data.local.room.UserDatabase
object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(dao)
    }
}