package com.academy.bangkit.mygithubuser.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.academy.bangkit.mygithubuser.data.network.response.User
import com.academy.bangkit.mygithubuser.databinding.FragmentFavoriteUserBinding
import com.academy.bangkit.mygithubuser.ui.ViewModelFactory
import com.academy.bangkit.mygithubuser.ui.adapter.UserAdapter


class FavoriteUserFragment : Fragment() {

    private var _favoriteUserBinding: FragmentFavoriteUserBinding? = null
    private val favoriteUserBinding get() = _favoriteUserBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _favoriteUserBinding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        return favoriteUserBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        with(favoriteUserBinding) {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(requireActivity())
        }

        favoriteViewModel.allUsers().observe(viewLifecycleOwner) { users ->
            val items = arrayListOf<User>()
            users.map {
                val item = User(id = it.id, login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            favoriteUserBinding.rvUser.adapter = UserAdapter(items)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteUserBinding = null
    }
}