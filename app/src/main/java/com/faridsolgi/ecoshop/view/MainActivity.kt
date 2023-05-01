package com.faridsolgi.ecoshop.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.ActivityMainBinding
import com.faridsolgi.ecoshop.model.utils.navHost
import com.faridsolgi.ecoshop.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!
@Inject
lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.apply {
            cartListLiveData.observe(this@MainActivity){
                println(it)
            }
            totalCartItemLiveData.observe(this@MainActivity){
                if (it == null) {
                    binding.tvCartItemCounter.visibility = View.GONE
                    return@observe
                }
                binding.tvCartItemCounter.
                text = it.toString()
                if (it>0){
                    binding.tvCartItemCounter.visibility = View.VISIBLE
                }else{
                    binding.tvCartItemCounter.visibility = View.GONE
                }
            }

        }

        binding.btnBag.setOnClickListener {
            navHost(binding.navHostFragmentContainer)
                .navController.navigate(R.id.action_global_shoppingCartFragment)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onNavigateUp(): Boolean {
        return navHost(binding.root).navController.navigateUp() || super.onNavigateUp()
    }
}