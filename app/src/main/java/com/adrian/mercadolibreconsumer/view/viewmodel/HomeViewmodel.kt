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
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val getCategoriesUsecase: GetCategoriesUsecase,
    private val getProductsByCategoryUsecase: GetProductsByCategoryUsecase
) : ViewModel() {

    private val _currentItem: MutableLiveData<Item> = MutableLiveData()
    val currentItem: LiveData<Item> = _currentItem

    private val _itemsDisplayingText: MutableLiveData<String> = MutableLiveData("Categoria: ")
    val itemsDisplayingLabel: LiveData<String> = _itemsDisplayingText

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
                        _itemsDisplayingText.value = "Categoria: ${currentCategory.name}"
                        getProductsByCategoryUsecase.invoke(currentCategory.id)
                            .collect {
                                _recommendedProducts.value = it
                            }
                    }
                    is Output.Failure -> {
                        _recommendedProducts.value = Output.Failure("Failed to get categories: ${categories.errorMessage}")
                    }
                    else -> {
                        // do waiting stuff
                    }
                }
            }
        }
    }

    init {
        getRecommendedProducts()
    }

    fun goToProductDetail(item: Item) {
        _currentItem.value = item
    }

    private fun pickRandomCategory(categories: List<Category>) =
        categories[Random(System.nanoTime()).nextInt(0, categories.size - 1)]


}