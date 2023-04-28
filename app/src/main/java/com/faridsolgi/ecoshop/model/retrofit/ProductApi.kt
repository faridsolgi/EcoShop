package com.faridsolgi.ecoshop.model.retrofit

import com.faridsolgi.ecoshop.model.ProductResponse
import com.faridsolgi.ecoshop.model.ProductSortEnum
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("/products/categories")
    suspend fun getCategories(): Response<List<String>?>


    @GET("/products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<ProductResponse>?>

    @GET("/products/{id}")
    suspend fun getProductDetailById(@Path("id") id: Int?): Response<ProductResponse?>

    @GET("/products")
    suspend fun getAllProduct(): Response<List<ProductResponse>?>


    @GET("/products")
    suspend fun getProductWithSort(@Query("sort") productSortEnum: ProductSortEnum): Response<List<ProductResponse>?>


}