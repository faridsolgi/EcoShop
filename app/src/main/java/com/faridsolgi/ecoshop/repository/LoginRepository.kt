package com.faridsolgi.ecoshop.repository

import com.faridsolgi.ecoshop.model.retrofit.LoginApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val  loginApi: LoginApi
) {


    suspend fun authUser(username : String, password :String) = flow{
        emit(loginApi.authUser(username,password))

    }

}