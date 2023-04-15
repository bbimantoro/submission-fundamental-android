package com.academy.bangkit.mygithubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.academy.bangkit.mygithubuser.data.UserRepository
import com.academy.bangkit.mygithubuser.data.local.entity.UserEntity

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun allUsers(): LiveData<List<UserEntity>> = userRepository.allUsers()
}