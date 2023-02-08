package com.adrian.mercadolibreconsumer.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrian.mercadolibreconsumer.R
import dagger.hilt.EntryPoint

@EntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}