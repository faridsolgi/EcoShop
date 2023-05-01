package com.faridsolgi.ecoshop.repository

import com.faridsolgi.ecoshop.model.ProductResponse
import com.faridsolgi.ecoshop.model.retrofit.ProductApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductRepository @Inject constructor(private val productApi: ProductApi) {




   suspend fun addNewProduct(productResponse: ProductResponse) =flow{
        emit(productApi.addNewProduct(productResponse))
    }
}