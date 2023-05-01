package com.faridsolgi.ecoshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import com.faridsolgi.ecoshop.repository.ShoppingCartRepository
import javax.inject.Inject

class ShoppingCartViewModel @Inject constructor(val repository: ShoppingCartRepository) :ViewModel() {

    val cartListLiveData : LiveData<List<ShoppingCart>>
            = repository.getCartList().asLiveData()
    val totalCartItemLiveData : LiveData<Int>
            = repository.getTotalCartItem().asLiveData()
    val totalCartAmountLiveData : LiveData<Float>
            = repository.getTotalCartAmount().asLiveData()

  suspend fun removeCartQuantity(shoppingCart: ShoppingCart){
        repository.removeCartQuantity(shoppingCart)
    }
    suspend fun addToCart(shoppingCart: ShoppingCart,quantity :Int = 1){
        repository.addToCert(shoppingCart,quantity)
    }
}