package com.academy.bangkit.mygithubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.databinding.ItemUserBinding
import com.academy.bangkit.mygithubuser.source.network.response.User
import com.bumptech.glide.Glide

class UserAdapter(
    private var listUser: List<User>
) :
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

    fun updateListUser(newListUser: List<User>) {
        val diffResult = DiffUtil.calculateDiff(diffCallback(listUser, newListUser))
        listUser = newListUser
        diffResult.dispatchUpdatesTo(this)
    }

    private fun diffCallback(
        oldList: List<User>,
        newList: List<User>
    ) = object : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
