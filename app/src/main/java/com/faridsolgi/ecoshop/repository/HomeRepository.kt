package com.faridsolgi.ecoshop.repository

import com.faridsolgi.ecoshop.model.retrofit.ProductApi
import com.faridsolgi.ecoshop.model.ProductSortEnum
import com.faridsolgi.ecoshop.model.room.EcoShopDataBase
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val productApi: ProductApi,
    private val roomDb: EcoShopDataBase
) {


    suspend fun getProductCategories() = flow {
        emit(productApi.getCategories())
    }

    suspend fun getProductByCategoryName(category: String) = flow {
        emit(productApi.getProductsByCategory(category))
    }

    suspend fun getProductDetailById(id: Int?) = flow {
        emit(productApi.getProductDetailById(id))
    }

    suspend fun getProductWithSort(productSortEnum: ProductSortEnum) = flow {
        emit(productApi.getProductWithSort(productSortEnum))
    }

    suspend fun getAllProduct() = flow {
        emit(productApi.getAllProduct())
    }


    suspend fun addToCert(shoppingCart: ShoppingCart) {
        roomDb.shoppingCertDao().insertOperation(shoppingCart)
    }
}