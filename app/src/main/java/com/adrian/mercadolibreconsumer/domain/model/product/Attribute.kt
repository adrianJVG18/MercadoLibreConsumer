package com.adrian.mercadolibreconsumer.domain.model.product

import com.google.gson.annotations.SerializedName

data class Attribute(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("value_name")
    val value: String = ""
)