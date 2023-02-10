package com.adrian.mercadolibreconsumer.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.mercadolibreconsumer.R
import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.databinding.FragmentProductsListBinding
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import com.adrian.mercadolibreconsumer.utils.viewBinding
import com.adrian.mercadolibreconsumer.view.adapter.SimpleItemAdapter
import com.adrian.mercadolibreconsumer.view.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsListFragment @Inject constructor(
) : Fragment(R.layout.fragment_products_list) {

    private val binding: FragmentProductsListBinding by viewBinding(FragmentProductsListBinding::bind)
    private val viewmodel: HomeViewmodel by activityViewModels()

    private val simpleItemAdapter: SimpleItemAdapter by lazy {
        SimpleItemAdapter(emptyList(), itemClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        initObservers()
    }

    private fun initObservers() {
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
            binding.itemsDisplayingLabel.text = it
        }
    }

    private val itemClickListener = object : SimpleItemAdapter.OnItemClickListener {
        override fun onItemClick(item: Item) {
            viewmodel.goToProductDetail(item)
        }

    }

    private fun setUpViews(){
        binding.productsListRecycler.adapter = simpleItemAdapter
        binding.productsListRecycler.layoutManager = LinearLayoutManager(context)
    }
}