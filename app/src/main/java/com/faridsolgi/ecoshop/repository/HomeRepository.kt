package com.faridsolgi.ecoshop.repository

import com.faridsolgi.ecoshop.model.retrofit.ProductApi
import com.faridsolgi.ecoshop.model.ProductSortEnum
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val productApi: ProductApi){



    suspend fun getProductCategories() = flow{
        emit(productApi.getCategories())
    }
    suspend fun getProductByCategoryName(category:String) = flow {
        emit(productApi.getProductsByCategory(category))
    }
    suspend fun getProductDetailById(id:Int?) = flow {
        emit(productApi.getProductDetailById(id))
    }
    suspend fun getProductWithSort(productSortEnum: ProductSortEnum) = flow {
        emit(productApi.getProductWithSort(productSortEnum))
    }
    suspend fun getAllProduct() = flow {
        emit(productApi.getAllProduct())
    }

}