package com.academy.bangkit.mygithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.academy.bangkit.mygithubuser.data.UserRepository
import com.academy.bangkit.mygithubuser.data.local.entity.UserEntity
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.academy.bangkit.mygithubuser.data.Result
import com.academy.bangkit.mygithubuser.data.network.retrofit.ApiConfig
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _result = MutableLiveData<Result<User>>()
    val result: LiveData<Result<User>> = _result
    fun getDetailUser(username: String) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = ApiConfig.getApiService().getDetailUser(username)
                _result.value = Result.Success(response)
            } catch (e: Exception) {
                Log.e(TAG, "getDetailUser : ${e.message.toString()}")
            }
        }
    }

    fun retrieveUser(username: String): LiveData<List<UserEntity>> =
        userRepository.retrieveUser(username)

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