package com.academy.bangkit.mygithubuser.ui.mutual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.academy.bangkit.mygithubuser.databinding.FragmentMutualBinding

class MutualFragment : Fragment() {

    private var _mutualFragment: FragmentMutualBinding? = null
    private val mutualFragment get() = _mutualFragment!!

    private val mutualViewModel: MutualViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _mutualFragment = FragmentMutualBinding.inflate(inflater, container, false)
        return mutualFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _mutualFragment = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val ARG_USERNAME = "username"
    }
}