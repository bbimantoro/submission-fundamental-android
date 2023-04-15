package com.academy.bangkit.mygithubuser.data.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("items")
    val items: List<User>
)