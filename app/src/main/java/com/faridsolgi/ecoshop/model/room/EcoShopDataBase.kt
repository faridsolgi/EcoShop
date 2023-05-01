package com.faridsolgi.ecoshop.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faridsolgi.ecoshop.model.room.dao.ShoppingCartDao
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart

@Database(entities = [ShoppingCart::class], version = 1)
abstract class EcoShopDataBase : RoomDatabase() {
    abstract fun shoppingCertDao() : ShoppingCartDao
}