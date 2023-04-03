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
import com.academy.bangkit.mygithubuser.ui.adapter.SectionsPagerAdapter
import com.academy.bangkit.mygithubuser.ui.adapter.UserAdapter
import com.bumptech.glide.Glide

class DetailUserFragment : Fragment() {

    private val viewModel: DetailViewModel by activityViewModels()

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = DetailUserFragmentArgs.fromBundle(arguments as Bundle).username

        viewModel.detailUser(username)

        setTabLayout()

        viewModel.user.observe(viewLifecycleOwner) { user ->
            setUserData(user)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setTabLayout() {
//        val sectionsPagerAdapter = SectionsPagerAdapter()
    }

    private fun setUserData(user: User) {

        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.ivAvatar)

        binding.apply {
            tvUsername.text = user.login
            tvName.text = user.name
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }
}