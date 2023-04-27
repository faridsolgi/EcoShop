package com.faridsolgi.ecoshop.model.retrofit

import com.faridsolgi.ecoshop.model.LoginRequest
import com.faridsolgi.ecoshop.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/auth/login")
    suspend fun authUser(@Body loginRequest: LoginRequest) : Response<LoginResponse?>
}