package com.academy.bangkit.mygithubuser.ui.theme

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.academy.bangkit.mygithubuser.data.SettingPreferences
import com.academy.bangkit.mygithubuser.databinding.FragmentThemeBinding
import com.academy.bangkit.mygithubuser.ui.SettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeFragment : Fragment() {

    private var _themeBinding: FragmentThemeBinding? = null
    private val themeBinding get() = _themeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _themeBinding = FragmentThemeBinding.inflate(inflater, container, false)

        return themeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val themeViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeBinding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeBinding.switchTheme.isChecked = false
            }
        }

        themeBinding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _themeBinding = null
    }
}