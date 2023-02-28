package com.redeyesncode.andromerce.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.AllSubCategoryResponse
import com.redeyesncode.andromerce.data.BannersResponseModel
import com.redeyesncode.andromerce.data.CategoryResponseModel
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.databinding.ActivityDashboardBinding
import com.redeyesncode.andromerce.databinding.BottomSheetLogoutBinding
import com.redeyesncode.andromerce.presentation.DashboardViewModel
import com.redeyesncode.andromerce.ui.adapters.BannerViewPager
import com.redeyesncode.andromerce.ui.adapters.CategoryAdapter
import com.redeyesncode.andromerce.ui.adapters.PopularProductAdapter
import com.redeyesncode.andromerce.ui.adapters.SubCategoryAdapter
import com.redeyesncode.andromerce.utils.AppSession

class DashboardActivity : BaseActivity(),PopularProductAdapter.onEvent {

    private lateinit var binding:ActivityDashboardBinding
    private lateinit var dashboardViewModel:DashboardViewModel

    override fun onClickProduct(position: Int, productId: Int) {
        val productDetailIntent = Intent(this@DashboardActivity,ProductDetailActivity::class.java)
        productDetailIntent.putExtra("PRODUCT_ID",productId.toString())
        startActivity(productDetailIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setupViewModel()
        attachObservers()
        initialApiCalls()
        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.topAppBar.setNavigationOnClickListener {
            showPopupMenu(it)
        }



        binding.tvViewAllProducts.setOnClickListener {
            startActivity(Intent(this@DashboardActivity,AllProductsActivity::class.java))
        }
        binding.topAppBar.setOnMenuItemClickListener(androidx.appcompat.widget.Toolbar.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.logoutMenu -> {

                    showBottomSheetLogout()
                }
                R.id.tvTermsConditions -> {
                    val url = getString(R.string.TERMS)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
                R.id.tvPrivacyPolicy -> {
                    val url = getString(R.string.PRIVACY)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
                R.id.viewAddress ->{
                    val intentViewAddress =Intent(this@DashboardActivity, ViewAddressActivity::class.java)
                    startActivity(intentViewAddress)


                }
                R.id.searchMenu ->{

                    val IntentSearch = Intent(this@DashboardActivity,SearchProductActivity::class.java)
                    startActivity(IntentSearch)

                }
                R.id.myProfile->{
                    val cartIntent = Intent(this@DashboardActivity,CartActivity::class.java)
                    startActivity(cartIntent)


                }
            }

            true
        })
    }

    private fun showBottomSheetLogout() {
        val bottomSheetLogoutBinding = BottomSheetLogoutBinding.inflate(LayoutInflater.from(this@DashboardActivity))
        val bottomSheetDialog = BottomSheetDialog(this@DashboardActivity,R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetLogoutBinding.root)
        bottomSheetLogoutBinding.btnLogout.setOnClickListener {
            AppSession(this@DashboardActivity).clear()
            val intentDashboard =Intent(this@DashboardActivity, LoginActivity::class.java)
            intentDashboard.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentDashboard)
            showToast("Logged Out !")

        }
        bottomSheetLogoutBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()





    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this@DashboardActivity, view)

        // Inflating popup menu from popup_menu.xml file

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.top_bar_menu_items, popupMenu.getMenu())

        // Showing the popup menu
        // Showing the popup menu
        popupMenu.show()
    }

    private fun initialApiCalls() {
        showLoader()
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
        binding.rvPopularProducts.adapter = PopularProductAdapter(this,it!!,this)
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