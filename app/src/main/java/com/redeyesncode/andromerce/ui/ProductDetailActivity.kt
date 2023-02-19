package com.redeyesncode.andromerce.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.ProductDetailResponseModel
import com.redeyesncode.andromerce.databinding.ActivityProductDetailBinding
import com.redeyesncode.andromerce.presentation.ProductDetailViewModel

class ProductDetailActivity : BaseActivity() {

    private lateinit var binding:ActivityProductDetailBinding
    private lateinit var productDetailViewModel: ProductDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        setupViewModel()
        attachObservers()
        initialApiCallandGetData()

        setContentView(binding.root)
    }

    private fun initialApiCallandGetData() {
        val hashMap = HashMap<String,String>()

        val productId = intent.getStringExtra("PRODUCT_ID")
        hashMap.put("product_id",productId.toString())
        showLoader()
        productDetailViewModel.getProductDetail(hashMap)



    }

    private fun setupViewModel() {
        productDetailViewModel = ProductDetailViewModel()
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        binding.viewmodel = productDetailViewModel



    }
    private fun attachObservers(){

        productDetailViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        productDetailViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }

        productDetailViewModel.productDetailResponse.observe((this)){
            hideLoader()
            showToast("Got the Response.")
            updateUi(it)



        }



    }

    private fun updateUi(it: ProductDetailResponseModel?) {
        binding.productName.text = it?.productTable?.productName
        binding.productDescription.text = it?.productTable?.description
        binding.tvSellingPrice.text = it?.productTable?.sellingPrice
        binding.tvMaxPrice.text = it?.productTable?.maxPrice

        Glide.with(this).load(it?.media?.get(0)?.link).placeholder(R.drawable.ic_placeholder).into(binding.ivMainImage)
        Glide.with(this).load(it?.category?.image).placeholder(R.drawable.ic_placeholder).into(binding.ivCategory)
        Glide.with(this).load(it?.subcategory?.image).placeholder(R.drawable.ic_placeholder).into(binding.ivSubCategory)

    }
}