package com.adrian.mercadolibreconsumer.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.adrian.mercadolibreconsumer.R
import com.adrian.mercadolibreconsumer.databinding.ActivityHomeBinding
import com.adrian.mercadolibreconsumer.utils.viewBinding
import com.adrian.mercadolibreconsumer.view.adapter.SimpleItemAdapter
import com.adrian.mercadolibreconsumer.view.fragment.ProductsListFragment
import com.adrian.mercadolibreconsumer.view.fragment.SearchToolBarFragment
import com.adrian.mercadolibreconsumer.view.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by viewBinding(ActivityHomeBinding::inflate)

    private val viewmodel: HomeViewmodel by viewModels()

    @Inject
    lateinit var searchToolbarFragment: SearchToolBarFragment

    @Inject
    lateinit var productsListFragment: ProductsListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        updateHomeContainer(productsListFragment)
    }

    private fun updateHomeContainer(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.homeContentContainer.id, fragment)
            .commit()
    }
}