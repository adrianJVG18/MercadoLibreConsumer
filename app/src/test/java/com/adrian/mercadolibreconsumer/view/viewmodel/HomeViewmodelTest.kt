package com.adrian.mercadolibreconsumer.view.viewmodel

import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.domain.interactor.GetCategoriesUsecase
import com.adrian.mercadolibreconsumer.domain.interactor.GetProductsByCategoryUsecase
import com.adrian.mercadolibreconsumer.domain.interactor.GetProductsByQueryUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

// TODO fix tests

@RunWith(JUnit4::class)
class HomeViewmodelTest {

    private lateinit var viewmodel: HomeViewmodel


    private lateinit var getCategoriesUsecase: GetCategoriesUsecase

    private lateinit var getProductsByCategoriesUsecase: GetProductsByCategoryUsecase

    private lateinit var getProductsByQueryUsecaseUsecase: GetProductsByQueryUsecase

    @Before
    fun setUp() {
        manualMocking()
        viewmodel = HomeViewmodel(
            getCategoriesUsecase = getCategoriesUsecase,
            getProductsByCategoryUsecase = getProductsByCategoriesUsecase,
            getProductsByQueryUsecase = getProductsByQueryUsecaseUsecase
        )
    }

    @Test
    fun `when getRecommendedProducts invoked, at least recommendedProducts starts loading`() = runBlocking {
        viewmodel.getRecommendedProducts()
        Assert.assertTrue(viewmodel.recommendedProducts.value is Output.Loading)
        Assert.assertTrue((viewmodel.recommendedProducts.value as Output.Loading).isLoading)
    }


    private fun manualMocking(){
        getCategoriesUsecase = mock(GetCategoriesUsecase::class.java)
        getProductsByCategoriesUsecase = mock(GetProductsByCategoryUsecase::class.java)
        getProductsByQueryUsecaseUsecase = mock(GetProductsByQueryUsecase::class.java)
    }

}
