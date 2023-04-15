package com.academy.bangkit.mygithubuser.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.academy.bangkit.mygithubuser.data.Result
import com.academy.bangkit.mygithubuser.data.network.retrofit.ApiConfig
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _result = MutableLiveData<Result<List<User>>>()
    val result: LiveData<Result<List<User>>> = _result
    fun getUserBySearch(q: String) {
        viewModelScope.launch {
            _result.value = Result.Loading
            try {
                val response = ApiConfig.getApiService().getUserBySearch(q)
                _result.value = Result.Success(response.items)
            } catch (e: Exception) {
                Log.d(TAG, "getUserBySearch : ${e.message.toString()}")
                _result.value = Result.Error
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}