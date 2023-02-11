package com.adrian.mercadolibreconsumer.domain.interactor

import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.data.framework.retrofit.implementation.RemoteProductsRepository
import com.adrian.mercadolibreconsumer.data.repository.ProductsRepository
import com.adrian.mercadolibreconsumer.domain.model.product.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUsecase @Inject constructor(
    private val productsRepository: RemoteProductsRepository
) {

    suspend fun execute(): Flow<Output<List<Category>>> = flow {
        emit(Output.Loading(true))
        productsRepository.getCategories().collect {
            when (it) {
                is Output.Success -> {
                    emit(Output.Success(it.data))
                }
                is Output.Failure -> {
                    emit(Output.Failure(it.errorMessage))
                }
                else -> {
                    // do onLoading stuff
                }
            }
        }
    }

}