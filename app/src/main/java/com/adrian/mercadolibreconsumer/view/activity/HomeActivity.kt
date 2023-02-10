package com.adrian.mercadolibreconsumer.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.adrian.mercadolibreconsumer.databinding.ActivityHomeBinding
import com.adrian.mercadolibreconsumer.utils.viewBinding
import com.adrian.mercadolibreconsumer.view.fragment.HomeFragment
import com.adrian.mercadolibreconsumer.view.fragment.ProductDetailFragment
import com.adrian.mercadolibreconsumer.view.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by viewBinding(ActivityHomeBinding::inflate)

    private val viewmodel: HomeViewmodel by viewModels()

    @Inject
    lateinit var homeFragment: HomeFragment

    lateinit var detailFragment: ProductDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        updateHomeContainer(homeFragment)
        setObservers()
    }

    private fun setObservers() {
        viewmodel.currentItem.observe(this) { item ->
            detailFragment = ProductDetailFragment(item)
            updateHomeContainer(detailFragment)
        }
    }

    private fun updateHomeContainer(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.homeContentContainer.id, fragment)
            .addToBackStack(null)
            .commit()
    }
}