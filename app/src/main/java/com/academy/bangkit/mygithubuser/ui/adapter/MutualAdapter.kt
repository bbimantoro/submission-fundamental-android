package com.academy.bangkit.mygithubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.databinding.ItemMutualUserBinding
import com.academy.bangkit.mygithubuser.helper.UserDiffCallback
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.bumptech.glide.Glide

class MutualAdapter : RecyclerView.Adapter<MutualAdapter.ViewHolder>() {

    private val listUser = ArrayList<User>()
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

    fun setListUser(newListUser: List<User>) {
        val diffCallback = UserDiffCallback(this.listUser, newListUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(newListUser)
        diffResult.dispatchUpdatesTo(this)
    }
}
