package com.academy.bangkit.mygithubuser.ui.mutual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.academy.bangkit.mygithubuser.databinding.FragmentMutualBinding

class MutualFragment : Fragment() {

    private var _mutualBinding: FragmentMutualBinding? = null
    private val mutualBinding get() = _mutualBinding!!

    private val mutualViewModel: MutualViewModel by activityViewModels()

    private var position: Int = 0
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _mutualBinding = FragmentMutualBinding.inflate(inflater, container, false)
        return mutualBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_TAB)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {

        }

        mutualViewModel.mutualUser.observe(viewLifecycleOwner) {

        }

        mutualViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        mutualBinding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _mutualBinding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val ARG_USERNAME = "username"
    }
}