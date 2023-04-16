package com.academy.bangkit.mygithubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.academy.bangkit.mygithubuser.data.network.response.User

class UserDiffCallback(
    private val oldUserList: List<User>,
    private val newUserList: List<User>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldUserList.size

    override fun getNewListSize() = newUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldUserList[oldItemPosition].login == newUserList[newItemPosition].login

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldUserList[oldItemPosition] == newUserList[newItemPosition]
}