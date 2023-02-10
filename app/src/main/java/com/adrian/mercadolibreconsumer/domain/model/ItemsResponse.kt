package com.adrian.mercadolibreconsumer.domain.model

import com.adrian.mercadolibreconsumer.domain.model.product.Item
import com.google.gson.annotations.SerializedName

data class ItemsResponse(
    @SerializedName("results")
    val results: List<Item>
)