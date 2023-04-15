package com.academy.bangkit.mygithubuser.data

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    object Error : Result<Nothing>()
    object Loading : Result<Nothing>()
}
