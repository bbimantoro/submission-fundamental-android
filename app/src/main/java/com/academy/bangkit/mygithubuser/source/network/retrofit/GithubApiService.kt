package com.academy.bangkit.mygithubuser.source.network.retrofit

import com.academy.bangkit.mygithubuser.source.network.response.User
import com.academy.bangkit.mygithubuser.source.network.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface GithubApiService {
    @GET("search/users")
    fun getUserBySearch(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<User>

    @GET("users/{login}/{type}")
    fun getMutual(
        @Path("login") login: String,
        @Path("type") type: String
    ): Call<List<User>>
}