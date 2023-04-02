package com.academy.bangkit.mygithubuser.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.databinding.FragmentHomeBinding
import com.academy.bangkit.mygithubuser.source.network.response.User
import com.academy.bangkit.mygithubuser.ui.adapter.UserAdapter

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!

    private val listUser = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setRecyclerViewData(listUser)

        setObserverListUser()

        setObserverIsLoading()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    homeViewModel.searchUser(it)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setRecyclerViewData(users: List<User>) {

        homeBinding.rvUser.setHasFixedSize(true)

        if (requireActivity().applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            homeBinding.rvUser.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            homeBinding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
        }

        val adapter = UserAdapter(users)
        homeBinding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {

                val username = data.login!!
                val toDetailUserFragment =
                    HomeFragmentDirections.actionHomeFragmentToDetailUserFragment()
                toDetailUserFragment.username = username
                findNavController().navigate(toDetailUserFragment)

            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        homeBinding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setObserverListUser() {
        homeViewModel.listUser.observe(viewLifecycleOwner) {
            setRecyclerViewData(it)
        }
    }

    private fun setObserverIsLoading() {
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _homeBinding = null
    }
}