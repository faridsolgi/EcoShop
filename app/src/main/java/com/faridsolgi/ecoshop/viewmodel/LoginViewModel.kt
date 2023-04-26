package com.faridsolgi.ecoshop.viewmodel

import androidx.lifecycle.ViewModel
import com.faridsolgi.ecoshop.repository.LoginRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(val loginRepository: LoginRepository) : ViewModel() {



    suspend fun authUser(username:String ,password:String){
         loginRepository.authUser(username,password)
    }
}