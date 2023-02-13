package com.adrian.mercadolibreconsumer.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrian.mercadolibreconsumer.databinding.ItemSimpleProductCardBinding
import com.adrian.mercadolibreconsumer.domain.model.product.Item
import com.adrian.mercadolibreconsumer.utils.toPriceTag
import com.squareup.picasso.Picasso

class SimpleItemAdapter(
    private var items: List<Item> = emptyList(),
    private val onItemClickListener: OnItemClickListener
): RecyclerView.Adapter<SimpleItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemViewHolder {
        val binding = ItemSimpleProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleItemViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    fun updateList(items: ArrayList<Item>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class SimpleItemViewHolder(
    private val binding: ItemSimpleProductCardBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item, clickListener: SimpleItemAdapter.OnItemClickListener) {
        binding.itemTitle.text = item.title
        binding.itemPrice.text = item.price.toPriceTag()
        val condition = item.attributes.find { it.id == "ITEM_CONDITION" }
        if (condition != null && condition.value.isNotEmpty()) {
            binding.itemCondition.visibility = View.VISIBLE
            binding.itemCondition.text = condition.value
        }

        binding.itemParentCard.setOnClickListener {
            clickListener.onItemClick(item)
        }

        val context: Context = binding.itemThumbnail.context
        Picasso.get().isLoggingEnabled = true
        Picasso.get()
            .load(item.thumbnail.replace("http://", "https://"))
            .placeholder(binding.itemThumbnail.drawable)
            .into(binding.itemThumbnail)


    }
}