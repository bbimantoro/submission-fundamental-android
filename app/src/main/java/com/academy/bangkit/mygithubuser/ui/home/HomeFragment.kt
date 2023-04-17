package com.academy.bangkit.mygithubuser.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.academy.bangkit.mygithubuser.R
import com.academy.bangkit.mygithubuser.databinding.FragmentHomeBinding
import com.academy.bangkit.mygithubuser.ui.adapter.UserAdapter
import com.academy.bangkit.mygithubuser.data.Result
import com.academy.bangkit.mygithubuser.data.network.response.User

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var adapter: UserAdapter
    private val listUser = ArrayList<User>()

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setRecyclerViewData()

        homeViewModel.result.observe(viewLifecycleOwner) { result ->
            setUserData(result)
        }
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
                    homeViewModel.getUserBySearch(it)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                findNavController().navigate(R.id.action_home_dest_to_favoriteUserFragment)
                true
            }
            R.id.theme -> {
                findNavController().navigate(R.id.action_home_dest_to_themeFragment)
                true
            }
            else -> true
        }
    }

    private fun setRecyclerViewData() {
        homeBinding.rvUser.setHasFixedSize(true)

        if (requireActivity().applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            homeBinding.rvUser.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            homeBinding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
        }

        adapter = UserAdapter(listUser)
        homeBinding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                data.login?.let {
                    val toDetailUserFragment =
                        HomeFragmentDirections.actionHomeFragmentToDetailUserFragment(it)
                    findNavController().navigate(toDetailUserFragment)
                }
            }
        })


    }

    private fun setUserData(result: Result<List<User>>) {
        when (result) {
            is Result.Loading -> {
                homeBinding.progressbar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                homeBinding.progressbar.visibility = View.GONE
                val listUser = result.data
                adapter.setListUser(listUser)
            }
            is Result.Error -> {
                with(homeBinding) {
                    progressbar.visibility = View.GONE
                    ivStatusError.visibility = View.VISIBLE
                    ivStatusError.setImageResource(R.drawable.ic_connection_error)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _homeBinding = null
    }
}