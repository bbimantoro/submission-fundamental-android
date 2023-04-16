package com.academy.bangkit.mygithubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var username: String? = null,

    @ColumnInfo("avatar_url")
    var avatarUrl: String? = null
)