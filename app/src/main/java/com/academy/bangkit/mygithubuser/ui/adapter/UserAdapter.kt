package com.academy.bangkit.mygithubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.databinding.ItemUserBinding
import com.academy.bangkit.mygithubuser.helper.UserDiffCallback
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listUser[position]
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            with(binding) {
                ivAvatar.loadImage(data.avatarUrl)
                tvUsername.text = data.login
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.broken_image_24)
            .into(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setListUser(newListUser: List<User>) {
        val diffCallback = UserDiffCallback(listUser, newListUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listUser.clear()
        listUser.addAll(newListUser)
        diffResult.dispatchUpdatesTo(this)
    }
}
