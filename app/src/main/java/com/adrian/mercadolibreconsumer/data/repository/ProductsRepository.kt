package com.adrian.mercadolibreconsumer.data.repository

import com.adrian.mercadolibreconsumer.domain.model.product.Category
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import com.adrian.mercadolibreconsumer.data.Output
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsByCategory(categoryId: String): Flow<Output<List<Item>>>
    suspend fun getRequestedProducts(searchText: String): Flow<Output<List<Item>>>
    suspend fun getCategories(): Flow<Output<List<Category>>>
}