package com.academy.bangkit.mygithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.academy.bangkit.mygithubuser.data.UserRepository
import com.academy.bangkit.mygithubuser.data.local.entity.UserEntity
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.academy.bangkit.mygithubuser.data.network.retrofit.ApiConfig
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().getDetailUser(username)
                _isLoading.value = false
                _user.value = response
            } catch (e: Exception) {
                _isLoading.value = true
                Log.e(TAG, "getDetailUser : ${e.message.toString()}")
            }
        }
    }

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            userRepository.deleteUser(id)
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

}