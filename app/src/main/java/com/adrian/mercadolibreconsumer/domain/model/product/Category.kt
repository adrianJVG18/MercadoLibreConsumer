package com.adrian.mercadolibreconsumer.domain.model.product

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)