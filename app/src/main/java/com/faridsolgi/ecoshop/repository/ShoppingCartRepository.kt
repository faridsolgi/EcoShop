package com.faridsolgi.ecoshop.repository

import com.faridsolgi.ecoshop.model.room.EcoShopDataBase
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import javax.inject.Inject

class ShoppingCartRepository @Inject constructor(private val roomDB : EcoShopDataBase) {
    fun getCartList()= roomDB.shoppingCertDao().getAll()
    fun getTotalCartItem() = roomDB.shoppingCertDao().getTotalCartItem()
    fun getTotalCartAmount() = roomDB.shoppingCertDao().getTotalCartAmount()
   suspend fun removeCartQuantity(shoppingCart: ShoppingCart) = roomDB.shoppingCertDao().deleteOperation(shoppingCart)
    suspend fun addToCert(shoppingCart: ShoppingCart,quantity:Int = 1) {
        roomDB.shoppingCertDao().insertOperation(shoppingCart,quantity)
    }
}