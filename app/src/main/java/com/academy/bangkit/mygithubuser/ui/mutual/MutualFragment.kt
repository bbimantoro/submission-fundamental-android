package com.academy.bangkit.mygithubuser.ui.mutual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.academy.bangkit.mygithubuser.databinding.FragmentMutualBinding
import com.academy.bangkit.mygithubuser.ui.adapter.MutualAdapter
import com.academy.bangkit.mygithubuser.data.Result
import com.google.android.material.divider.MaterialDividerItemDecoration

class MutualFragment : Fragment() {

    private var _mutualBinding: FragmentMutualBinding? = null
    private val mutualBinding get() = _mutualBinding!!

    private lateinit var adapter: MutualAdapter

    private val mutualViewModel: MutualViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _mutualBinding = FragmentMutualBinding.inflate(inflater, container, false)
        return mutualBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = arguments?.getString(ARG_USERNAME)

        setRecyclerViewData()

        if (position == 1) {
            username?.let { mutualViewModel.getFollowersUser(it) }
            mutualViewModel.result.observe(viewLifecycleOwner) { result ->
                setUserData(result)
            }
        } else {
            username?.let { mutualViewModel.getFollowingUser(it) }
            mutualViewModel.result.observe(viewLifecycleOwner) { result ->
                setUserData(result)
            }
        }
    }

    private fun setRecyclerViewData() {
        val divider =
            MaterialDividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)

        adapter = MutualAdapter()

        with(mutualBinding) {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(requireActivity())
            rvUser.addItemDecoration(divider)
            rvUser.adapter = adapter
        }
    }

    private fun setUserData(result: Result<List<User>>) {
        when (result) {
            is Result.Loading -> {
                mutualBinding.progressbar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                mutualBinding.progressbar.visibility = View.GONE

                val listUser = result.data
                adapter.setListUser(listUser)
            }
            else -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mutualBinding = null
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}