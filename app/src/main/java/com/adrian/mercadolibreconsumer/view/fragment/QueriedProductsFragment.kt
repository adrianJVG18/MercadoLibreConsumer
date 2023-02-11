package com.adrian.mercadolibreconsumer.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.mercadolibreconsumer.R
import com.adrian.mercadolibreconsumer.data.Output
import com.adrian.mercadolibreconsumer.databinding.FragmentHomeBinding
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import com.adrian.mercadolibreconsumer.utils.viewBinding
import com.adrian.mercadolibreconsumer.view.adapter.SimpleItemAdapter
import com.adrian.mercadolibreconsumer.view.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QueriedProductsFragment @Inject constructor(
) : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
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
        viewmodel.queriedItems.observe(viewLifecycleOwner) {
            when (it) {
                is Output.Success -> {
                    val message = "Resultados de: ${viewmodel.query.value}"
                    binding.productsContentHost.itemsDisplayingLabel.text = message
                    simpleItemAdapter.updateList(it.data as ArrayList<Item>)
                }
                is Output.Failure -> {
                    binding.productsContentHost.itemsDisplayingLabel.text = ""
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // do waiting stuff
                }
            }
        }
    }

    private val itemClickListener = object : SimpleItemAdapter.OnItemClickListener {
        override fun onItemClick(item: Item) {
            viewmodel.goToProductDetail(item)
        }

    }

    private fun setUpViews(){
        binding.productsContentHost.productsListRecycler.adapter = simpleItemAdapter
        binding.productsContentHost.productsListRecycler.layoutManager = LinearLayoutManager(context)
    }
}