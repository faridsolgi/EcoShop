package com.faridsolgi.ecoshop.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true) var id :Int?=null,
    var productId :Int,
    var name:String? = null,
    var price :Float? = null ,
    var imageUrl:String?=null,
    var count :Int = 0
):java.io.Serializable
