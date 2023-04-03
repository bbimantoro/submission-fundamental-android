package com.academy.bangkit.mygithubuser.ui.mutual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.academy.bangkit.mygithubuser.databinding.FragmentMutualBinding
import com.academy.bangkit.mygithubuser.source.network.response.User
import com.academy.bangkit.mygithubuser.ui.adapter.MutualAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration

class MutualFragment : Fragment() {

    private var _binding: FragmentMutualBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MutualAdapter

    private val viewModel: MutualViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMutualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = arguments?.getString(ARG_USERNAME)

        setRecyclerViewData()


        if (position == 1) {
            viewModel.listUser.observe(viewLifecycleOwner) { followers ->
                adapter.updateListUser(followers)
            }
            username?.let { viewModel.followersUser(it) }
        } else {
            viewModel.listUser.observe(viewLifecycleOwner) { following ->
                adapter.updateListUser(following)
            }
            username?.let { viewModel.followingUser(it) }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setRecyclerViewData() {
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
        val divider =
            MaterialDividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        binding.rvUser.addItemDecoration(divider)

        adapter = MutualAdapter(mutableListOf())
        binding.rvUser.adapter = adapter
    }

    private fun setFollowersData(user: List<User>) {
        val adapter = MutualAdapter(user)
        binding.rvUser.adapter = adapter
    }

    private fun setFollowingUserData(user: List<User>) {
        val adapter = MutualAdapter(user)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}