package com.academy.bangkit.mygithubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.academy.bangkit.mygithubuser.databinding.ItemUserBinding
import com.academy.bangkit.mygithubuser.source.network.response.User
import com.bumptech.glide.Glide

class MutualAdapter(private val users: List<User>) :
    RecyclerView.Adapter<MutualAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = users[position]
        holder.bind(data)
    }

    class ListViewHolder(private var mutualAdapterBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(mutualAdapterBinding.root) {

        fun bind(data: User) {
            mutualAdapterBinding.apply {
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .into(ivAvatar)
                tvUsername.text = data.login
            }
        }
    }
}