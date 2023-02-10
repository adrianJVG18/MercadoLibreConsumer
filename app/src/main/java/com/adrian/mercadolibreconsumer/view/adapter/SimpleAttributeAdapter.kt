package com.adrian.mercadolibreconsumer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrian.mercadolibreconsumer.databinding.ItemSimpleItemAttributeBinding
import com.adrian.mercadolibreconsumer.domain.model.product.Attribute

class SimpleAttributeAdapter(
    private val attributes: List<Attribute>
) : RecyclerView.Adapter<SimpleAttributeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAttributeViewHolder {
        val binding = ItemSimpleItemAttributeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleAttributeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleAttributeViewHolder, position: Int) {
        holder.bind(attributes[position])
    }

    override fun getItemCount(): Int = attributes.size

}

class SimpleAttributeViewHolder(
    private val binding: ItemSimpleItemAttributeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(attribute: Attribute) {
        binding.attributeName.text = attribute.name
        binding.attributeValue.text = attribute.value
    }

}