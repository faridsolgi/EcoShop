package com.faridsolgi.ecoshop.model.room.dao

import androidx.room.*
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

@Dao
interface ShoppingCartDao {

    @Query("Select * from shoppingCart")
    fun getAll(): Flow<List<ShoppingCart>>

    @Query("Select sum(count*price) from shoppingCart")
    fun getTotalCartAmount(): Flow<Float>

    @Query("Select sum(count) from shoppingCart")
    fun getTotalCartItem(): Flow<Int>

    @Query("Select * from shoppingCart where productId = :productId")
    fun getByProductId(productId: Int): Flow<ShoppingCart>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun _insert(shoppingCart: ShoppingCart)

    @Update
   suspend fun _update(shoppingCart: ShoppingCart)

    @Delete
  suspend  fun _delete(shoppingCart: ShoppingCart)

    suspend fun insertOperation(shoppingCart: ShoppingCart,quantity : Int = 1) {
        val shopItem = getByProductId(shoppingCart.productId).firstOrNull()
        if (shopItem == null) {
            _insert(shoppingCart.apply { count = quantity })
        } else {
            shopItem.count +=quantity
            _update(shopItem)
        }


    }

    suspend fun deleteOperation(shoppingCart: ShoppingCart)  {
        val shopItem = getByProductId(shoppingCart.productId).firstOrNull()
        if (shopItem != null) {
            if (shopItem !=null && shopItem?.count!! >1){

                _update(shopItem.apply {shopItem.count -= 1})
            } else
                _delete(shopItem)
        }



    }


}