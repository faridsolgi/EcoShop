package com.faridsolgi.ecoshop.viewmodel

import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import com.faridsolgi.ecoshop.repository.ProductDetailRepository
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(val repository: ProductDetailRepository) {
    suspend fun addToCart(shoppingCart: ShoppingCart){
        repository.addToCert(shoppingCart)
    }
}