package com.adrian.mercadolibreconsumer.domain.interactor

import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.data.framework.retrofit.implementation.RemoteProductsRepository
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsByQueryUsecase @Inject constructor(
    private val productsRepository: RemoteProductsRepository
) {

    suspend fun execute(query: String): Flow<Output<List<Item>>> = flow {
        emit(Output.Loading(true))
        productsRepository.getRequestedProducts(query).collect {
            when (it) {
                is Output.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        emit(Output.Failure("No products were found with those terms"))
                    } else {
                        emit(Output.Success(it.data))
                    }
                }
                is Output.Failure -> {
                    emit(Output.Failure("Service Unavailable: ${it.errorMessage}"))
                }
                else -> {
                    // loading stuff
                }
            }
        }
    }

}