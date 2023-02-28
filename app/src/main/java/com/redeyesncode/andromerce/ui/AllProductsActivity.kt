package com.redeyesncode.andromerce.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.CategoryResponseModel
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.databinding.ActivityAllProductsBinding
import com.redeyesncode.andromerce.databinding.BottomSheetFilterBinding
import com.redeyesncode.andromerce.presentation.AllProductsViewModel
import com.redeyesncode.andromerce.ui.adapters.AllProductAdapter
import com.redeyesncode.andromerce.ui.adapters.CategoryListAdapter
import kotlin.collections.ArrayList

class AllProductsActivity : BaseActivity(),CategoryListAdapter.onEvent ,AllProductAdapter.onEvent{


    lateinit var binding:ActivityAllProductsBinding
    lateinit var allProductsViewModel: AllProductsViewModel
    lateinit var bottomSheetDialog :BottomSheetDialog
    var categoryNames =ArrayList<CategoryResponseModel.Data>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setupViewModel()
        initClicks()
        attachObservers()
        getDataAndUpdateUi()
        setContentView(binding.root)
    }

    private fun getDataAndUpdateUi() {
        bottomSheetDialog = BottomSheetDialog(this,R.style.BottomSheetDialogTheme)

        val adapterNumber = intent.getIntExtra("ADAPTER_NUMBER",-1)
        val id = intent.getIntExtra("ID",-1)
        binding.commonTitleBar.tvTitle.text = intent.getStringExtra("NAME")

        if(adapterNumber==0){
            showLoader()
            binding.fabFilter.visibility = View.GONE
            val hashMap = HashMap<String,String>()
            hashMap.put("category_id",id.toString())
            allProductsViewModel.getProductByCategory(hashMap)

        }else if(adapterNumber==1){
            binding.fabFilter.visibility = View.VISIBLE
            initialApiCall()

        }else if(adapterNumber==2){
            showLoader()
            binding.fabFilter.visibility = View.GONE
            val hashMap = HashMap<String,String>()
            hashMap.put("subcategory_id",id.toString())
            allProductsViewModel.getProductBySubCategory(hashMap)
        }


    }

    private fun initClicks() {
        binding.fabFilter.setOnClickListener {
            if(bottomSheetDialog!=null){
                bottomSheetDialog.show()
            }
        }
        binding.commonTitleBar.tvTitle.text = "All Products"
        binding.commonTitleBar.backIcon.setOnClickListener { finish() }
    }

    override fun onProductClick(position: Int, productId: String) {
        val productDetailIntent = Intent(this@AllProductsActivity,ProductDetailActivity::class.java)
        productDetailIntent.putExtra("PRODUCT_ID",productId)
        startActivity(productDetailIntent)
    }

    override fun onCategoryClick(position: Int, categoryId: String) {

        showLoader()
        if(categoryId.toInt()==-1){
            allProductsViewModel.getAllProductsDetail()
        }else{
            val hashMap = HashMap<String,String>()
            hashMap.put("category_id",categoryId)
            allProductsViewModel.getProductByCategory(hashMap)
        }
    }

    private fun initialApiCall() {
        showLoader()
        allProductsViewModel.getAllProductsDetail()
        allProductsViewModel.getAllCategory()
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
        allProductsViewModel.categoryProductsResponseModel.observe((this)){
            hideLoader()
            // As we always have default products. no need to add null check.
            if(bottomSheetDialog.isShowing){
                bottomSheetDialog.dismiss()

            }
            setupAdapter(it)

        }
        allProductsViewModel.popularProductResponse.observe((this)){
            hideLoader()
            // As we always have default products. no need to add null check.
            setupAdapter(it)

        }
        allProductsViewModel.categoryResponseModel.observe((this)){

            hideLoader()
            categoryNames.add(CategoryResponseModel.Data(-1,"All","",""))
            categoryNames.addAll(it.data)
            val adapterNumber = intent.getIntExtra("ADAPTER_NUMBER",-1)
            setupBottomSheetAdapter(categoryNames)





        }


    }

    private fun setupBottomSheetAdapter(data: ArrayList<CategoryResponseModel.Data>) {
        val bottomSheetBinding = BottomSheetFilterBinding.inflate(LayoutInflater.from(this))
        bottomSheetDialog = BottomSheetDialog(this,R.style.BottomSheetDialogTheme)
        bottomSheetBinding.recvCategory.adapter = CategoryListAdapter(this@AllProductsActivity,data,this)
        bottomSheetBinding.recvCategory.layoutManager = LinearLayoutManager(this@AllProductsActivity,LinearLayoutManager.VERTICAL,false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)


    }

    private fun setupAdapter(it: PopularProductResponse?) {
        binding.recvAllProducts.adapter = AllProductAdapter(this@AllProductsActivity,it!!,this)
        binding.recvAllProducts.layoutManager = GridLayoutManager(this,2)
    }

    private fun setupViewModel() {
        allProductsViewModel = AllProductsViewModel()
        allProductsViewModel = ViewModelProvider(this).get(AllProductsViewModel::class.java)
        binding.viewmodel = allProductsViewModel
    }
}