package com.faridsolgi.ecoshop.repository

import com.faridsolgi.ecoshop.model.room.EcoShopDataBase
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import javax.inject.Inject

class ProductDetailRepository @Inject constructor(val roomDb :EcoShopDataBase) {
    suspend fun addToCert(shoppingCart: ShoppingCart) {
        roomDb.shoppingCertDao().insertOperation(shoppingCart)
    }
}