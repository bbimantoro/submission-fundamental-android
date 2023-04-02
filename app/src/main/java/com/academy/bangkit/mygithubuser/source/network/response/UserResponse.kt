package com.academy.bangkit.mygithubuser.source.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("items")
    val items: List<User>

)