package com.faridsolgi.ecoshop.repository

import com.faridsolgi.ecoshop.model.LoginRequest
import com.faridsolgi.ecoshop.model.retrofit.LoginApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val  loginApi: LoginApi
) {
    suspend fun authUser(loginRequest: LoginRequest) = flow {
        emit(loginApi.authUser(loginRequest))
    }

}