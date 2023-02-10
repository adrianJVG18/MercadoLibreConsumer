package com.adrian.mercadolibreconsumer.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adrian.mercadolibreconsumer.R
import com.adrian.mercadolibreconsumer.view.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchToolBarFragment @Inject constructor(

): Fragment(R.layout.fragment_search_toolbar){

    private val viewmodel: HomeViewmodel by viewModels()

}