package com.adrian.mercadolibreconsumer.domain.model.user

import com.google.gson.annotations.SerializedName

data class Seller(
    @SerializedName("id")
    val id: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("ratings")
    val rating: Rating
)