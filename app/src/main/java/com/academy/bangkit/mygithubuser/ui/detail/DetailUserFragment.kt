package com.academy.bangkit.mygithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.data.Result
import com.academy.bangkit.mygithubuser.databinding.FragmentDetailUserBinding
import com.academy.bangkit.mygithubuser.ui.ViewModelFactory
import com.academy.bangkit.mygithubuser.data.local.entity.UserEntity
import com.academy.bangkit.mygithubuser.ui.adapter.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserFragment : Fragment() {

    private var dataUser: UserEntity? = null
    private var btnFabState = false

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
            when (result) {
                is Result.Loading -> {
                    detailUserBinding.progressbar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    detailUserBinding.progressbar.visibility = View.GONE

                    Glide.with(this)
                        .load(result.data.avatarUrl)
                        .into(detailUserBinding.ivAvatar)

                    with(detailUserBinding) {
                        tvUsername.text = result.data.login
                        tvName.text = result.data.name
                        tvFollowers.text = result.data.followers.toString()
                        tvFollowing.text = result.data.following.toString()

                        fabFavorite.setOnClickListener {
                            if (!btnFabState) {
                                btnFabState = true
                                fabFavorite.setImageResource(R.drawable.favorite_24)

                                dataUser = UserEntity(
                                    result.data.id,
                                    result.data.login,
                                    result.data.avatarUrl
                                )
                                detailViewModel.insertUser(dataUser as UserEntity)
                                showToast(getString(R.string.added))
                            } else {
                                btnFabState = false
                                fabFavorite.setImageResource(R.drawable.favorite_border_24)
                                detailViewModel.deleteUser(result.data.id)
                                showToast(getString(R.string.deleted))
                            }
                        }
                    }
                }
                else -> {}
            }
        }

        detailViewModel.retrieveUser(username).observe(viewLifecycleOwner) { users ->
            btnFabState = users.isNotEmpty()
            if (btnFabState) {
                detailUserBinding.fabFavorite.setImageResource(R.drawable.favorite_24)
            } else {
                detailUserBinding.fabFavorite.setImageResource(R.drawable.favorite_border_24)
            }
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

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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