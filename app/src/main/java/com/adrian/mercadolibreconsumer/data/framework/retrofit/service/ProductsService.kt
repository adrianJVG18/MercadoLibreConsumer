package com.adrian.mercadolibreconsumer.data.framework.retrofit.service

import com.adrian.mercadolibreconsumer.domain.model.ItemsResponse
import com.adrian.mercadolibreconsumer.domain.model.product.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {
    @GET("search?")
    suspend fun getRequestedProducts(
        @Query(value = "q") searchText: String
    ): ItemsResponse

    @GET("search?")
    suspend fun getProductsByCategory(
        @Query(value = "category") categoryId: String
    ): ItemsResponse

    @GET("categories")
    suspend fun getCategories(): List<Category>
}