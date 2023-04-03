package com.academy.bangkit.mygithubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.databinding.ItemMutualUserBinding
import com.academy.bangkit.mygithubuser.source.network.response.User
import com.bumptech.glide.Glide

class MutualAdapter(private var listUser: List<User>) :
    RecyclerView.Adapter<MutualAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMutualUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listUser[position]
        holder.bind(data)

    }

    inner class ViewHolder(private var binding: ItemMutualUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: User) {
            with(binding) {
                tvUsername.text = data.login
                ivAvatar.loadImage(data.avatarUrl)
            }
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.broken_image_24)
            .into(this)
    }

    fun updateListUser(newListUser: List<User>) {
        val diffResult = DiffUtil.calculateDiff(diffCallback(listUser, newListUser))
        listUser = newListUser
        diffResult.dispatchUpdatesTo(this)
    }

    private fun diffCallback(
        oldList: List<User>,
        newList: List<User>,
    ) = object : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    }
}
