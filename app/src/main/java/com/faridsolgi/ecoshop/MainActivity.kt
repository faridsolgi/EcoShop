package com.faridsolgi.ecoshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faridsolgi.ecoshop.databinding.ActivityMainBinding
import com.faridsolgi.ecoshop.model.utils.navHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onNavigateUp(): Boolean {
        return navHost(binding.root).navController.navigateUp() || super.onNavigateUp()
    }
}