package com.adrian.mercadolibreconsumer.data.framework.retrofit.implementation

import com.adrian.mercadolibreconsumer.data.framework.retrofit.service.ProductsService
import com.adrian.mercadolibreconsumer.data.repository.ProductsRepository
import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.domain.model.product.Category
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class RemoteProductsRepository @Inject constructor(
    private val meLiApi: ProductsService
): ProductsRepository {

    override suspend fun getProductsByCategory(categoryId: String): Flow<Output<List<Item>>> = flow {
        emit(Output.Loading(true))
        val response = meLiApi.getProductsByCategory(categoryId)
        emit(Output.Success(response.results))
    }.catch { e ->
        emit(Output.Failure(e.message ?: "Unknown error"))
    }

    override suspend fun getRequestedProducts(searchText: String): Flow<Output<List<Item>>> = flow {

        emit(Output.Loading(true))
        val response = meLiApi.getRequestedProducts(searchText)
        emit(Output.Success(response.results))
    }.catch { e ->
        emit(Output.Failure(e.message ?: "Unknown error"))
    }

    override suspend fun getCategories(): Flow<Output<List<Category>>> = flow {
        emit(Output.Loading(true))
        val response = meLiApi.getCategories()
        emit(Output.Success(response))
    }.catch { e ->
        emit(Output.Failure(e.message ?: "Unknown error"))
    }
}