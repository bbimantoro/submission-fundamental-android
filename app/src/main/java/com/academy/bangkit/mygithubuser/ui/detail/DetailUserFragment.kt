package com.academy.bangkit.mygithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.databinding.FragmentDetailUserBinding
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.academy.bangkit.mygithubuser.ui.ViewModelFactory
import com.academy.bangkit.mygithubuser.data.Result
import com.academy.bangkit.mygithubuser.ui.adapter.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserFragment : Fragment() {

    private var _detailUserBinding: FragmentDetailUserBinding? = null
    private val detailUserBinding get() = _detailUserBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _detailUserBinding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return detailUserBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        setTabLayoutView()

        val username = DetailUserFragmentArgs.fromBundle(arguments as Bundle).username

        detailViewModel.getDetailUser(username)

        detailViewModel.result.observe(viewLifecycleOwner) { result ->
            setUserData(result)
        }

    }

    private fun setTabLayoutView() {
        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager, lifecycle)
        sectionsPagerAdapter.username =
            DetailUserFragmentArgs.fromBundle(arguments as Bundle).username
        detailUserBinding.layoutMutual.viewpager.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            detailUserBinding.layoutMutual.tabs,
            detailUserBinding.layoutMutual.viewpager
        ) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setUserData(result: Result<User>) {
        when (result) {
            is Result.Loading -> {
                detailUserBinding.progressbar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                detailUserBinding.progressbar.visibility = View.GONE

                Glide.with(this)
                    .load(result.data.avatarUrl)
                    .into(detailUserBinding.ivAvatar)

                detailUserBinding.apply {
                    tvUsername.text = result.data.name
                    tvName.text = result.data.name
                    tvFollowers.text = result.data.followers.toString()
                    tvFollowing.text = result.data.following.toString()
                }
            }
            else -> {}
        }
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