package com.faridsolgi.ecoshop.view

import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.FragmentLoginBinding
import com.faridsolgi.ecoshop.model.LoginRequest
import com.faridsolgi.ecoshop.model.utils.alertUser
import com.faridsolgi.ecoshop.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var viewModel: LoginViewModel

    companion object {
        private const val TAG = "LoginFragment"
    }

    override fun fragmentBody() {
        //invisible toolbar on login
        val mainActivity = activity as MainActivity
        mainActivity.binding.appBarLayout.visibility= View.GONE





        viewModel.loginResult.observe(viewLifecycleOwner) {
            Log.i(TAG, "fragmentBody: loginResult :SUCCESS")
            mainActivity.binding.appBarLayout.visibility= View.VISIBLE

            binding.root.findNavController()
                .navigate(R.id.action_loginFragment_to_mainFragment)
        }
        viewModel.loginFailResult.observe(viewLifecycleOwner) {
            alertUser(view = binding.inputPassword, it)
        }

        binding.ctaLogin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val userName = binding.inputUserName.editText?.text.toString()
                val password = binding.inputPassword.editText?.text.toString()
                if (userName.isNotEmpty() && password.isNotEmpty())
                    viewModel.authUser(LoginRequest(userName, password))
                else if (userName.isEmpty()) {
                    alertUser(view = binding.inputUserName, msg = getString(R.string.fill_username))
                } else if (password.isEmpty()) {
                    alertUser(view = binding.inputPassword, msg = getString(R.string.fill_password))
                }
            }
        }
    }


}