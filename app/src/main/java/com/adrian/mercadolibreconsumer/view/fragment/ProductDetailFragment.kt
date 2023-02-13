package com.adrian.mercadolibreconsumer.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.mercadolibreconsumer.R
import com.adrian.mercadolibreconsumer.databinding.FragmentProductDetailBinding
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import com.adrian.mercadolibreconsumer.utils.toPriceTag
import com.adrian.mercadolibreconsumer.utils.viewBinding
import com.adrian.mercadolibreconsumer.view.adapter.SimpleAttributeAdapter
import com.adrian.mercadolibreconsumer.view.viewmodel.ProductDetailViewmodel
import com.squareup.picasso.Picasso

class ProductDetailFragment(
    private val item: Item
) : Fragment(R.layout.fragment_product_detail) {

    private val viewmodel: ProductDetailViewmodel by viewModels()
    private val binding: FragmentProductDetailBinding by viewBinding(FragmentProductDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayItem()
    }

    private fun displayItem() {
        Picasso.get()
            .load(item.thumbnail.replace("http://", "https://"))
            .into(binding.productImageView)
        binding.productTitle.text = item.title
        binding.productPrice.text = item.price.toPriceTag()
        if (item.attributes.isEmpty()) {
            binding.attributesLabel.visibility = View.GONE
            binding.attributesList.visibility = View.GONE
        } else {
            binding.attributesLabel.visibility = View.VISIBLE
            binding.attributesList.visibility = View.VISIBLE
            binding.attributesList.adapter = SimpleAttributeAdapter(item.attributes)
            binding.attributesList.layoutManager = LinearLayoutManager(context)
        }
    }


}