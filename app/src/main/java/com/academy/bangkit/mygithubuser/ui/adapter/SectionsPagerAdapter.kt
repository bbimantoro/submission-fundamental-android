package com.academy.bangkit.mygithubuser.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.academy.bangkit.mygithubuser.ui.detail.DetailUserFragment
import com.academy.bangkit.mygithubuser.ui.mutual.MutualFragment

private const val TAB_COUNT = 2
private const val INCREMENT_INT = 1

class SectionsPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = MutualFragment()
        fragment.arguments = Bundle().apply {
            putInt(MutualFragment.ARG_TAB, position + INCREMENT_INT)
            putString(MutualFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount() = TAB_COUNT
}