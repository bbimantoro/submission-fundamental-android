package com.academy.bangkit.mygithubuser.ui.mutual

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.academy.bangkit.mygithubuser.data.Result
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.academy.bangkit.mygithubuser.data.network.retrofit.ApiConfig
import kotlinx.coroutines.launch

class MutualViewModel : ViewModel() {

    private val _result = MutableLiveData<Result<List<User>>>()
    val result: LiveData<Result<List<User>>> = _result

    fun getFollowersUser(username: String) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = ApiConfig.getApiService().getMutual(username, "followers")
                _result.value = Result.Success(response)
            } catch (e: Exception) {
                Log.d(TAG, "getFollowersUser: ${e.message.toString()}")
            }
        }
    }

    fun getFollowingUser(username: String) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = ApiConfig.getApiService().getMutual(username, "following")
                _result.value = Result.Success(response)
            } catch (e: Exception) {
                Log.d(TAG, "getFollowingUser: ${e.message.toString()}")
            }
        }
    }

    companion object {
        private const val TAG = "MutualViewModel"
    }
}