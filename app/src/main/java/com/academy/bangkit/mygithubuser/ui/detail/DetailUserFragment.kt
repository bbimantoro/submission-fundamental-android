package com.academy.bangkit.mygithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.academy.bangkit.mygithubuser.databinding.FragmentDetailUserBinding
import com.academy.bangkit.mygithubuser.source.network.response.User
import com.bumptech.glide.Glide

class DetailUserFragment : Fragment() {

    private val detailViewModel by viewModels<DetailViewModel>()

    private lateinit var dataUsername: String

    private var _detailUserBinding: FragmentDetailUserBinding? = null
    private val detailUserBinding get() = _detailUserBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _detailUserBinding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return detailUserBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataUsername = DetailUserFragmentArgs.fromBundle(arguments as Bundle).username

        detailViewModel.user.observe(viewLifecycleOwner) { user ->
            setUserData(user)
        }
    }

    private fun setUserData(user: User) {
        detailUserBinding.apply {
            tvUsername.text = dataUsername
            tvName.text = user.name
            tvCompany.text = user.company
            tvLocation.text = user.location
            tvFollowers.text = user.followersUrl
            tvFollowing.text = user.followingUrl
        }
        Glide.with(this)
            .load(user.avatarUrl)
            .into(detailUserBinding.ivAvatar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _detailUserBinding = null
    }
}