package com.faridsolgi.ecoshop.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("price")
	val price: Float? = null,

	@field:SerializedName("rating")
	val rating: Rating? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null
):java.io.Serializable

data class Rating(

	@field:SerializedName("rate")
	val rate: Float? = null,

	@field:SerializedName("count")
	val count: Int? = null
):java.io.Serializable

