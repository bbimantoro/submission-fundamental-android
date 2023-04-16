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
import com.academy.bangkit.mygithubuser.databinding.FragmentDetailUserBinding
import com.academy.bangkit.mygithubuser.data.network.response.User
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

        detailViewModel.user.observe(viewLifecycleOwner) { user ->
            setUserData(user)

            with(detailUserBinding) {
                fabFavorite.setOnClickListener {
                    if (!btnFabState) {
                        btnFabState = true
                        fabFavorite.setImageResource(R.drawable.favorite_24)

                        dataUser = UserEntity(
                            user.id,
                            user.login,
                            user.avatarUrl
                        )
                        detailViewModel.insertUser(dataUser as UserEntity)
                        showToast(getString(R.string.added))
                    } else {
                        btnFabState = false
                        fabFavorite.setImageResource(R.drawable.favorite_border_24)
                        detailViewModel.deleteUser(user.id)
                        showToast(getString(R.string.deleted))
                    }
                }
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
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

    private fun setUserData(data: User) {

        Glide.with(this)
            .load(data.avatarUrl)
            .into(detailUserBinding.ivAvatar)

        detailUserBinding.apply {
            tvUsername.text = data.name
            tvName.text = data.name
            tvFollowers.text = data.followers.toString()
            tvFollowing.text = data.following.toString()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        detailUserBinding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
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