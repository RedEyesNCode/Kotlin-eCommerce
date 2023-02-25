package com.redeyesncode.andromerce.ui

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.GetAllProductsResponseModel
import com.redeyesncode.andromerce.databinding.ActivitySearchProductBinding
import com.redeyesncode.andromerce.presentation.SearchViewModel
import com.redeyesncode.andromerce.ui.adapters.SearchAdapter


class SearchProductActivity : BaseActivity(), SearchAdapter.onEvent {
    private lateinit var binding:ActivitySearchProductBinding
    private lateinit var searchViewModel: SearchViewModel
    override fun onSearchClick(position: Int, productId: String) {
        val productDetailIntent = Intent(this@SearchProductActivity,ProductDetailActivity::class.java)
        productDetailIntent.putExtra("PRODUCT_ID",productId)
        startActivity(productDetailIntent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchProductBinding.inflate(layoutInflater)
        initClicks()
        setupViewmodel()
        attachObservers()
        setContentView(binding.root)
    }

    private fun attachObservers() {
        searchViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        searchViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        searchViewModel.searchProductResponse.observe((this)){
            hideLoader()
            if(it!=null){
                if(it.data.isEmpty()){
                    showToast("No Products Found !")
                }else{
                    setupSearchAdapter(it)

                }

            }else{
                showToast("No Products Found !")
            }



        }


    }

    private fun setupSearchAdapter(getAllProductsResponseModel: GetAllProductsResponseModel) {
        binding.recvSearch.adapter = SearchAdapter(this,getAllProductsResponseModel.data,this)
        binding.recvSearch.layoutManager = LinearLayoutManager(this@SearchProductActivity,LinearLayoutManager.VERTICAL,false)


    }

    private fun setupViewmodel() {
        searchViewModel = SearchViewModel()
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.viewmodel = searchViewModel



    }

    private fun initClicks() {
        binding.backIcon.setOnClickListener { finish() }

        binding.searchView.setOnSearchClickListener {
            showToast("Search click")
        }
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                showLog("Query is submitted")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // call the search product api
                val hashMap = HashMap<String,String>()

                hashMap.put("query",binding.searchView.query.toString())

                searchViewModel.searchProduct(hashMap)

                return true


            }
        })


    }
}