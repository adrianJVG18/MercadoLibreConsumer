package com.adrian.mercadolibreconsumer.view.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.mercadolibreconsumer.R
import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.databinding.FragmentHomeBinding
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import com.adrian.mercadolibreconsumer.utils.hideKeyboard
import com.adrian.mercadolibreconsumer.utils.viewBinding
import com.adrian.mercadolibreconsumer.view.activity.HomeActivity
import com.adrian.mercadolibreconsumer.view.adapter.SimpleItemAdapter
import com.adrian.mercadolibreconsumer.view.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(

) : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private val viewmodel: HomeViewmodel by activityViewModels()

    private val simpleItemAdapter: SimpleItemAdapter by lazy {
        SimpleItemAdapter(emptyList(), itemClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setObservers()
    }

    private fun setObservers() {
        viewmodel.recommendedProducts.observe(viewLifecycleOwner) {
            when (it) {
                is Output.Success -> {
                    simpleItemAdapter.updateList(it.data as ArrayList<Item>)
                }
                is Output.Failure -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // do waiting stuff
                }
            }
        }
        viewmodel.itemsDisplayingLabel.observe(viewLifecycleOwner) {
            binding.productsContentHost.itemsDisplayingLabel.text = it
        }

        viewmodel.queriedItems.observe(viewLifecycleOwner) {
            if (it is Output.Loading && it.isLoading) {
                val queriedFragment = QueriedProductsFragment()
                (activity as HomeActivity).updateHomeContainer(queriedFragment)
            }
        }
    }

    private val itemClickListener = object : SimpleItemAdapter.OnItemClickListener {
        override fun onItemClick(item: Item) {
            viewmodel.goToProductDetail(item)
        }

    }

    private fun setUpViews(){
        binding.searchToolbarHost.viewmodel = this.viewmodel
        binding.searchToolbarHost.lifecycleOwner = this
        binding.productsContentHost.productsListRecycler.adapter = simpleItemAdapter
        binding.productsContentHost.productsListRecycler.layoutManager = LinearLayoutManager(context)

        binding.searchToolbarHost.searchEditText.setOnEditorActionListener { _, keyCode, event ->
            if (((event?.action ?: -1) == KeyEvent.ACTION_DOWN) || keyCode == EditorInfo.IME_ACTION_SEARCH
            ) {
                binding.searchToolbarHost.searchEditText.hideKeyboard()
                viewmodel.searchItemsByQuery()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}