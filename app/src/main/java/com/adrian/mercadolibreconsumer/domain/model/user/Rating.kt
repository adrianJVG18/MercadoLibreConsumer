package com.adrian.mercadolibreconsumer.domain.model.user

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("positive")
    val positive: Float,
    @SerializedName("negative")
    val negative: Float,
    @SerializedName("neutral")
    val neutral: Float
)