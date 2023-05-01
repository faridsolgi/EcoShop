package com.faridsolgi.ecoshop.repository

import com.faridsolgi.ecoshop.model.room.EcoShopDataBase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainActivityRepository @Inject constructor(private  val roomDb : EcoShopDataBase) {

    fun getCartList()= roomDb.shoppingCertDao().getAll()
    fun getTotalCartItem() = roomDb.shoppingCertDao().getTotalCartItem()
}