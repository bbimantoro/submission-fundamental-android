package com.academy.bangkit.mygithubuser.data.network.retrofit

import com.academy.bangkit.mygithubuser.data.network.response.User
import com.academy.bangkit.mygithubuser.data.network.response.UserResponse
import retrofit2.http.*

interface GithubApiService {
    @GET("search/users")
    suspend fun getUserBySearch(
        @Query("q") q: String
    ): UserResponse

    @GET("users/{login}")
    suspend fun getDetailUser(
        @Path("login") login: String
    ): User

    @GET("users/{login}/{type}")
    suspend fun getMutual(
        @Path("login") login: String,
        @Path("type") type: String
    ): List<User>
}