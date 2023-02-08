package com.adrian.mercadolibreconsumer.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrian.mercadolibreconsumer.R

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail_fragment)
    }
}