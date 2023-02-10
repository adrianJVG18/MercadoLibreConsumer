package com.adrian.mercadolibreconsumer.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.domain.interactor.GetCategoriesUsecase
import com.adrian.mercadolibreconsumer.domain.interactor.GetProductsByCategoryUsecase
import com.adrian.mercadolibreconsumer.domain.model.product.Category
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val getCategoriesUsecase: GetCategoriesUsecase,
    private val getProductsByCategoryUsecase: GetProductsByCategoryUsecase
) : ViewModel() {

    private val _currentCategory: MutableStateFlow<Category> = MutableStateFlow(Category("", ""))
    val currentCategory = _currentCategory.asStateFlow()

    private val _recommendedProducts: MutableLiveData<Output<List<Item>>> =
        MutableLiveData(Output.Loading(false))
    val recommendedProducts: LiveData<Output<List<Item>>> = _recommendedProducts

    fun getRecommendedProducts() {
        viewModelScope.launch {
            _recommendedProducts.value = Output.Loading(true)
            getCategoriesUsecase.invoke().collect { categories ->
                when (categories) {
                    is Output.Success -> {
                        val currentCategory = pickRandomCategory(categories.data)
                        _currentCategory.value = currentCategory
                        getProductsByCategoryUsecase.invoke(currentCategory.id)
                            .collect {
                                _recommendedProducts.value = it
                            }
                    }
                    is Output.Failure -> {
                        _recommendedProducts.value = Output.Failure("Failed to fetch Categories")
                    }
                    else -> {
                        // do waiting stuff
                    }
                }
            }
        }
    }

    private fun pickRandomCategory(categories: List<Category>) =
        categories[Random(System.nanoTime()).nextInt(0, categories.size - 1)]


}