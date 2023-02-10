package com.adrian.mercadolibreconsumer.domain.interactor

import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.data.framework.retrofit.implementation.RemoteProductsRepository
import com.adrian.mercadolibreconsumer.data.repository.ProductsRepository
import com.adrian.mercadolibreconsumer.domain.model.product.Category
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsByCategoryUsecase @Inject constructor(
    private val productsRepository: RemoteProductsRepository
) {

    suspend fun invoke(categoryId: String): Flow<Output<List<Item>>> = flow {
        emit(Output.Loading(true))
        productsRepository.getProductsByCategory(categoryId).collect {
            when (it) {
                is Output.Success -> {
                    if (it.data.isEmpty()) {
                        emit(Output.Failure("Invalid Category param provided"))
                    } else {
                        emit(Output.Success(it.data))
                    }
                }
                is Output.Failure -> {
                    emit(Output.Failure("Service Unavailable"))
                }
                else -> {
                    // do onLoading stuff
                }
            }
        }
    }

}