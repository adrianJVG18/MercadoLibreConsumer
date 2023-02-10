package com.adrian.mercadolibreconsumer.view.fragment

import androidx.fragment.app.Fragment
import com.adrian.mercadolibreconsumer.R
import com.adrian.mercadolibreconsumer.databinding.FragmentHomeBinding
import com.adrian.mercadolibreconsumer.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(

) : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

}