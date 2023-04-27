package com.faridsolgi.ecoshop.view

import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    override fun fragmentBody() {
    val navHostFragment= childFragmentManager.findFragmentById(R.id.nav_home_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigateUp()

    }
}