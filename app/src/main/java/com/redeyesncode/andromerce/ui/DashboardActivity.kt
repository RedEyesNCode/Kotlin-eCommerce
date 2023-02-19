package com.redeyesncode.andromerce.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.AllSubCategoryResponse
import com.redeyesncode.andromerce.data.BannersResponseModel
import com.redeyesncode.andromerce.data.CategoryResponseModel
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.databinding.ActivityDashboardBinding
import com.redeyesncode.andromerce.presentation.DashboardViewModel
import com.redeyesncode.andromerce.ui.adapters.BannerViewPager
import com.redeyesncode.andromerce.ui.adapters.CategoryAdapter
import com.redeyesncode.andromerce.ui.adapters.PopularProductAdapter
import com.redeyesncode.andromerce.ui.adapters.SubCategoryAdapter

class DashboardActivity : BaseActivity() {

    private lateinit var binding:ActivityDashboardBinding
    private lateinit var dashboardViewModel:DashboardViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setupViewModel()
        attachObservers()
        initialApiCalls()
        setContentView(binding.root)
    }

    private fun initialApiCalls() {
        dashboardViewModel.getAllCategory()
        dashboardViewModel.getAllBanners()
        dashboardViewModel.getAllPopularProducts()
        dashboardViewModel.getAllSubCategory()

    }

    private fun setupViewModel() {
        dashboardViewModel = DashboardViewModel()
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding.viewmodel = dashboardViewModel
    }
    private fun attachObservers(){

        dashboardViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        dashboardViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        dashboardViewModel.categoryResponseModel.observe((this)){

            hideLoader()
            //setup the adapter for category
            if(it.data.isEmpty()){
                showToast("Categories not found !")
            }else{
                setupCategoryAdapter(it)

            }

        }
        dashboardViewModel.bannersResponseModel.observe((this)){


            hideLoader()
            //setup the adapter for category
            if(it.data.isEmpty()){
                showToast("Categories not found !")
            }else{
                setupBannerAdapterViewPager(it)

            }

        }
        dashboardViewModel.popularProductResponse.observe((this)){

            hideLoader()
            //setup the adapter for category
            if(it.data.isEmpty()){
                showToast("Products not found !")
            }else{
                setupPopularProductsAdapter(it)

            }

        }
        dashboardViewModel.subCategoryResponseModel.observe((this)){

            hideLoader()
            if(it.data.isEmpty()){
                showToast("Products not found !")
            }else{
                setupOtherCategoryAdapter(it)

            }


        }



    }

    private fun setupOtherCategoryAdapter(it: AllSubCategoryResponse?) {
        binding.rvSubCategory.adapter = SubCategoryAdapter(this@DashboardActivity, it!!)
        binding.rvSubCategory.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setupPopularProductsAdapter(it: PopularProductResponse?) {
        binding.rvPopularProducts.adapter = PopularProductAdapter(this,it!!)
        binding.rvPopularProducts.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    }

    private fun setupBannerAdapterViewPager(it: BannersResponseModel?) {
        binding.viewPagerBanner.adapter = BannerViewPager(this, it!!)
        binding.viewPagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val currentPageIndex = 0
        binding.viewPagerBanner.currentItem = currentPageIndex
        binding.viewPagerBanner.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    // Make ui for the dots.
                    binding.tvNumberImages.text = "${position + 1} / ${it.data.size}"

                }
            }
        )
    }

    private fun setupCategoryAdapter(it: CategoryResponseModel?) {
        binding.rvTopSeller.adapter = CategoryAdapter(this@DashboardActivity, it!!)
        binding.rvTopSeller.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

}