package com.adrian.mercadolibreconsumer.view.viewmodel

import androidx.lifecycle.*
import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.domain.interactor.GetCategoriesUsecase
import com.adrian.mercadolibreconsumer.domain.interactor.GetProductsByCategoryUsecase
import com.adrian.mercadolibreconsumer.domain.interactor.GetProductsByQueryUsecase
import com.adrian.mercadolibreconsumer.domain.model.product.Category
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val getCategoriesUsecase: GetCategoriesUsecase,
    private val getProductsByCategoryUsecase: GetProductsByCategoryUsecase,
    private val getProductsByQueryUsecase: GetProductsByQueryUsecase
) : ViewModel() {

    val query = MutableLiveData("")

    private val _currentItem: MutableLiveData<Item> = MutableLiveData()
    val currentItem: LiveData<Item> = _currentItem

    private val _queriedItems: MutableLiveData<Output<List<Item>>> = MutableLiveData(Output.Loading(false))
    val queriedItems: LiveData<Output<List<Item>>> = _queriedItems

    private val _itemsDisplayingText: MutableLiveData<String> = MutableLiveData("Categoria: ")
    val itemsDisplayingLabel: LiveData<String> = _itemsDisplayingText

    private val _recommendedProducts: MutableLiveData<Output<List<Item>>> =
        MutableLiveData(Output.Loading(false))
    val recommendedProducts: LiveData<Output<List<Item>>> = _recommendedProducts

    fun getRecommendedProducts() {
        viewModelScope.launch {
            _recommendedProducts.value = Output.Loading(true)
            getCategoriesUsecase.execute().collect { categories ->
                when (categories) {
                    is Output.Success -> {
                        val currentCategory = pickRandomCategory(categories.data)
                        _itemsDisplayingText.value = "Categoria: ${currentCategory.name}"
                        getProductsByCategoryUsecase.execute(currentCategory.id)
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

    fun searchItemsByQuery() {
        _queriedItems.value = Output.Loading(true)
        viewModelScope.launch {
            if (!query.value.isNullOrEmpty()) {
                getProductsByQueryUsecase.execute(query.value as String).collect {
                    when (it) {
                        is Output.Success -> {
                            _queriedItems.postValue(Output.Success(it.data))
                        }
                        is Output.Failure -> {
                            _queriedItems.postValue(Output.Failure(it.errorMessage))
                        }
                        else -> {
                            // do Loading stuff
                        }
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