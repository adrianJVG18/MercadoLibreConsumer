package com.adrian.mercadolibreconsumer.domain.model.product

import com.adrian.mercadolibreconsumer.domain.model.user.Address
import com.adrian.mercadolibreconsumer.domain.model.user.Rating
import com.adrian.mercadolibreconsumer.domain.model.user.Seller
import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val itemId: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("price")
    val price: Float = 0.0f,
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("seller")
    val seller: Seller = Seller("", "", Rating(0.0f, 0.0f, 1f)),
    @SerializedName("seller_address")
    val sellerAddress: Address = Address("Capital federal", "Almagro"),
    @SerializedName("attributes")
    val attributes: List<Attribute> = emptyList()
)