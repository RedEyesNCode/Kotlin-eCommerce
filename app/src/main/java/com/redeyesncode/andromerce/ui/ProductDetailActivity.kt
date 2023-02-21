package com.redeyesncode.andromerce.ui

import android.content.Context
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.ProductDetailResponseModel
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import com.redeyesncode.andromerce.databinding.ActivityProductDetailBinding
import com.redeyesncode.andromerce.databinding.BottomSheetSelectAddressBinding
import com.redeyesncode.andromerce.presentation.ProductDetailViewModel
import com.redeyesncode.andromerce.ui.adapters.AddressAdapter
import com.redeyesncode.andromerce.utils.AppSession

class ProductDetailActivity : BaseActivity(), AddressAdapter.onEventAddress {

    private lateinit var binding:ActivityProductDetailBinding
    private lateinit var productDetailViewModel: ProductDetailViewModel
    private lateinit var bottomSheetDialog:BottomSheetDialog
    override fun onDeleteClick(position: Int, addressId: Int) {
        showLog("OnDeletelClick")
    }

    override fun onEditClick(position: Int, data: UserAddressResponseModel.Data) {

        showLog("onEditClick")
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
        addressAdapter = AddressAdapter(this@ProductDetailActivity,this,data)

        setContentView(binding.root)
    }

    private fun initClicks() {

        binding.btnPlaceOrder.setOnClickListener {
            showPlaceOrderSheet()
        }
    }

    private fun showPlaceOrderSheet() {
        val bottomSheetBinding  = BottomSheetSelectAddressBinding.inflate(LayoutInflater.from(this@ProductDetailActivity))
        bottomSheetDialog = BottomSheetDialog(this,R.style.BottomSheetDialogTheme)

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
        val hashMap = HashMap<String,String>()
        showLoader()
        hashMap.put("user_id", AppSession(this@ProductDetailActivity).getUser()?.id.toString())



        bottomSheetBinding.recvAddress.adapter = addressAdapter
        bottomSheetBinding.recvAddress.layoutManager = LinearLayoutManager(this@ProductDetailActivity,LinearLayoutManager.VERTICAL,false)

        bottomSheetBinding.btnPlaceOrder.setTvButtonText("Place Order")


    }

    private fun initialApiCallandGetData() {
        val hashMap = HashMap<String,String>()

        val productId = intent.getStringExtra("PRODUCT_ID")
        hashMap.put("product_id",productId.toString())
        showLoader()
        productDetailViewModel.getProductDetail(hashMap)
        productDetailViewModel.getUserAddress(hashMap)



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

        productDetailViewModel.userAddressResponse.observe((this)){
            hideLoader()
            // update the recyclerview
            if(it.data.size!=0){
                addressAdapter = AddressAdapter(this@ProductDetailActivity,this,it.data)
                addressAdapter.notifyDataSetChanged()

            }else {
                showDialog("Record Not Found !","Important Alert !")
            }


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