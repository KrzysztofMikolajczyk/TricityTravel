/*
 * Copyright © 2019 by Agnieszka Maciejewska, Maciej Królik, Krzysztof Mikołajczyk. TricityTravel
 * This application is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package com.aib.tricitytravel.ui.settingsfragment

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.aib.tricitytravel.R
import com.aib.tricitytravel.databinding.FragmentSettingsBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SettingsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    private lateinit var binding: FragmentSettingsBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        setupViewModel()
        setupButtonsOnClickListeners()
        setupWeatherEditText()

        return binding.root
    }

    private fun setupViewModel() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

    private fun setupButtonsOnClickListeners() {
        binding.editStopsButton.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.action_settingsFragment_to_settingsSelectStopFragment)
        }

        binding.editWeatherButton.setOnClickListener {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            sharedPref?.edit()?.putString("prefWeatherCity", binding.weatherEditText.text.toString())?.apply()
            Toast.makeText(context, getString(R.string.city_changed), Toast.LENGTH_SHORT).show()
            Log.d("stop", sharedPref.getString("prefWeatherCity", "Gdańsk"))
        }
    }

    private fun setupWeatherEditText() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        binding.weatherEditText.setText(sharedPref?.getString("prefWeatherCity", "Gdańsk")!!)
    }
}
