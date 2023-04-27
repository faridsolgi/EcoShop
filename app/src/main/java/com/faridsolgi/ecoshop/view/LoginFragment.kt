package com.faridsolgi.ecoshop.view

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.FragmentLoginBinding
import com.faridsolgi.ecoshop.model.LoginRequest
import com.faridsolgi.ecoshop.model.utils.alertUser
import com.faridsolgi.ecoshop.model.utils.navHost
import com.faridsolgi.ecoshop.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var viewModel : LoginViewModel
    companion object{
        private const val TAG = "LoginFragment"
    }
    override fun fragmentBody() {

        viewModel.loginResult.observe(viewLifecycleOwner) {
            Log.i(TAG, "fragmentBody: loginResult :SUCCESS")
            binding.root.findNavController()
                .navigate(R.id.action_loginFragment_to_mainFragment)
        }
        viewModel.loginFailResult.observe(viewLifecycleOwner) {
            alertUser(view = binding.inputPassword,it)
        }

        binding.ctaLogin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val userName = binding.inputUserName.editText?.text.toString()
                val password = binding.inputPassword.editText?.text.toString()
                if (userName.isNotEmpty()  && password.isNotEmpty() )
                    viewModel.authUser(LoginRequest(userName,password))
                else if(userName.isEmpty()){
                    alertUser(view = binding.ctaLogin, msg = getString(R.string.fill_username))
                }else if(password.isEmpty()){
                    alertUser(view = binding.ctaLogin,msg = getString(R.string.fill_password))
                }
            }
        }
    }


}