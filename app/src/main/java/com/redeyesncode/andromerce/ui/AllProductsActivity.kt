package com.redeyesncode.andromerce.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.databinding.ActivityAllProductsBinding
import com.redeyesncode.andromerce.databinding.AllProductItemBinding
import com.redeyesncode.andromerce.presentation.AllProductsViewModel
import com.redeyesncode.andromerce.ui.adapters.AllProductAdapter

class AllProductsActivity : BaseActivity() {


    lateinit var binding:ActivityAllProductsBinding
    lateinit var allProductsViewModel: AllProductsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setupViewModel()
        attachObservers()
        initialApiCall()
        setContentView(R.layout.activity_all_products)
    }

    private fun initialApiCall() {
        allProductsViewModel.getAllProductsDetail()
    }

    private fun attachObservers() {
        allProductsViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        allProductsViewModel.isLoading.observe((this)){
            if(it){
                hideLoader()
            }else{
                hideLoader()
            }
        }
        allProductsViewModel.popularProductResponse.observe((this)){
            hideLoader()
            // As we always have default products. no need to add null check.
            setupAdapter(it)

        }


    }

    private fun setupAdapter(it: PopularProductResponse?) {
        binding.recvAllProducts.adapter = AllProductAdapter(this@AllProductsActivity,it!!)
        binding.recvAllProducts.layoutManager = LinearLayoutManager(this)


    }

    private fun setupViewModel() {
        allProductsViewModel = AllProductsViewModel()
        allProductsViewModel = ViewModelProvider(this).get(AllProductsViewModel::class.java)
        binding.viewmodel = allProductsViewModel
    }
}