package com.redeyesncode.andromerce.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.ProductDetailResponseModel
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import com.redeyesncode.andromerce.databinding.ActivityProductDetailBinding
import com.redeyesncode.andromerce.databinding.ImageDialogBinding
import com.redeyesncode.andromerce.presentation.ProductDetailViewModel
import com.redeyesncode.andromerce.ui.adapters.AddressAdapter
import com.redeyesncode.andromerce.ui.adapters.ProductImageViewPager

class ProductDetailActivity : BaseActivity(), AddressAdapter.onEventAddress, ProductImageViewPager.onImageEvent {

    private lateinit var binding:ActivityProductDetailBinding
    private lateinit var productDetailViewModel: ProductDetailViewModel
    private lateinit var bottomSheetDialog:BottomSheetDialog
    override fun onDeleteClick(position: Int, addressId: Int) {
        // Not used
        showLog("OnDeletelClick")
    }

    override fun onImageClick(position: Int, link: String) {
        val dialogBinding = ImageDialogBinding.inflate(LayoutInflater.from(this@ProductDetailActivity))
        val dialog = Dialog(this@ProductDetailActivity)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(dialogBinding.root)
        Glide.with(this).load(link).placeholder(R.drawable.ic_placeholder).into(dialogBinding.imageDialog)
        dialog.show()

    }

    override fun onEditClick(position: Int, data: UserAddressResponseModel.Data) {
        // Not used.
        showLog("onEditClick")
    }

    override fun onSelectAddress(position: Int, addressId: Int) {
        showLog("onSelectAddress")
    }

    private lateinit var addressAdapter: AddressAdapter


    var data = ArrayList<UserAddressResponseModel.Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        initClicks()
        setupViewModel()
        attachObservers()
        initialApiCallandGetData()
        addressAdapter = AddressAdapter(this@ProductDetailActivity,this,data,false)

        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.commonTitleBar.tvTitle.text = "Product Details"
        binding.commonTitleBar.backIcon.setOnClickListener { finish() }

        binding.btnAddToCart.setOnClickListener {
            var orderPlaceintent = Intent(this@ProductDetailActivity,CartAddressActivity::class.java)
            orderPlaceintent.putExtra("PRODUCT_ID",intent.getStringExtra("PRODUCT_ID"))

            startActivity(orderPlaceintent)



        }
    }

    private fun showPlaceOrderSheet() {


    }

    private fun initialApiCallandGetData() {
        val hashMap = HashMap<String,String>()

        val productId = intent.getStringExtra("PRODUCT_ID")
        hashMap.put("product_id",productId.toString())
        showLoader()
        productDetailViewModel.getProductDetail(hashMap)
//        productDetailViewModel.getUserAddress(hashMap)



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
            updateUi(it)



        }

        productDetailViewModel.userAddressResponse.observe((this)){
            hideLoader()
            // update the recyclerview
            if(it.data.size!=0){
                addressAdapter = AddressAdapter(this@ProductDetailActivity,this,it.data,false)
                addressAdapter.notifyDataSetChanged()

            }else {
                showDialog("Record Not Found !","Important Alert !")
            }


        }

    }

    private fun updateUi(it: ProductDetailResponseModel?) {
        binding.tvProductName.text = it?.productTable?.productName
        binding.tvDescription.text = it?.productTable?.description
        binding.tvMaxPrice.text = "${it?.productTable?.maxPrice}"
        binding.tvRealPrice.text = "₹ ${it?.productTable?.sellingPrice}"
        binding.tvMaxPrice.setPaintFlags(binding.tvMaxPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)




        binding.viewPagerProductImages.adapter =ProductImageViewPager(this,it!!.media,this)
        binding.viewPagerProductImages.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        for (index in it.media){
            binding.tabsProductImage.addTab(binding.tabsProductImage.newTab())
        }

        TabLayoutMediator(binding.tabsProductImage, binding.viewPagerProductImages) { tab, position ->
            binding.tabsProductImage.selectTab(tab)
        }.attach()



        binding.viewPagerProductImages.currentItem = 0

    }
}