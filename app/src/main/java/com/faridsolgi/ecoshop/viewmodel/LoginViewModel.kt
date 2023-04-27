package com.faridsolgi.ecoshop.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faridsolgi.ecoshop.model.LoginRequest
import com.faridsolgi.ecoshop.model.LoginResponse
import com.faridsolgi.ecoshop.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

  private  var _loginFailResult : MutableLiveData<String> =  MutableLiveData()
    var loginFailResult : LiveData<String> =  _loginFailResult
    private  var _loginResult : MutableLiveData<LoginResponse?> =  MutableLiveData()
    var loginResult : LiveData<LoginResponse?> =  _loginResult

    suspend fun authUser(loginRequest: LoginRequest){
        loginRepository.authUser(loginRequest).collectLatest {
            if(it.isSuccessful){
                _loginResult.postValue(it.body())
            }else{
                _loginFailResult.postValue(it.errorBody()?.string())
            }
        }
    }



}