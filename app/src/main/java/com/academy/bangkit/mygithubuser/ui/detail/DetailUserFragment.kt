package com.academy.bangkit.mygithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.activityViewModels
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.databinding.FragmentDetailUserBinding
import com.academy.bangkit.mygithubuser.source.network.response.User
import com.bumptech.glide.Glide

class DetailUserFragment : Fragment() {

    private val detailViewModel: DetailViewModel by activityViewModels()

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

        val username = DetailUserFragmentArgs.fromBundle(arguments as Bundle).username

        detailViewModel.detailUser(username)

        setObserverDataUser()

        setObserverIsLoading()
    }

    private fun setObserverIsLoading() {
        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        detailUserBinding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setObserverDataUser() {
        detailViewModel.user.observe(viewLifecycleOwner) { user ->
            setUserData(user)
        }
    }

    private fun setUserData(user: User) {
        detailUserBinding.apply {
            tvUsername.text = user.login
            tvName.text = user.name
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
        }
        Glide.with(this)
            .load(user.avatarUrl)
            .into(detailUserBinding.ivAvatar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _detailUserBinding = null
    }

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }
}