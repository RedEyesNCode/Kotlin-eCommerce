package com.redeyesncode.andromerce.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.OrderPlaceBody
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import com.redeyesncode.andromerce.databinding.ActivityCartAddressBinding
import com.redeyesncode.andromerce.presentation.OrderViewModel
import com.redeyesncode.andromerce.ui.adapters.AddressAdapter
import com.redeyesncode.andromerce.utils.AppSession

class CartAddressActivity : BaseActivity(), AddressAdapter.onEventAddress {

    private lateinit var binding:ActivityCartAddressBinding
    private lateinit var viewModel: OrderViewModel
    var addressIdActivity = -1
    override fun onDeleteClick(position: Int, addressId: Int) {

    }

    override fun onEditClick(position: Int, data: UserAddressResponseModel.Data) {

    }

    override fun onSelectAddress(position: Int, addressId: Int) {
        addressIdActivity = addressId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartAddressBinding.inflate(layoutInflater)
        initClicks()
        setupViewModel()
        attachObservers()
        initialApiCalls()
        setContentView(binding.root)
    }

    private fun initialApiCalls() {
        val hashMap = HashMap<String,String>()
        showLoader()
        hashMap.put("user_id", AppSession(this@CartAddressActivity).getUser()?.id.toString())
        viewModel.getUserAddress(hashMap)

    }

    private fun attachObservers() {
        viewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        viewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        viewModel.userAddressResponse.observe((this)){
            hideLoader()
            // update the recyclerview
            if(it.data.size!=0){
                setupAddressAdapter(it)
            }else {
                showDialog("Record Not Found !","Important Alert !")
            }


        }
        viewModel.commonStatusResponse.observe((this)){
            hideLoader()
            startActivity(Intent(this@CartAddressActivity,OrderPlacedActivity::class.java))



        }
    }

    private fun setupViewModel() {
        viewModel = OrderViewModel()
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        binding.viewmodel = viewModel

    }
    private fun setupAddressAdapter(it: UserAddressResponseModel?) {
        addressIdActivity = it?.data?.get(0)?.id!!.toInt()
        binding.recvAddress.adapter = AddressAdapter(this@CartAddressActivity,this,it.data,false)
        binding.recvAddress.layoutManager = LinearLayoutManager(this@CartAddressActivity,
            LinearLayoutManager.VERTICAL,false)
    }
    private fun initClicks() {
        binding.btnAddAddress.setOnClickListener {
            val addAddressIntent = Intent(this@CartAddressActivity,ViewAddressActivity::class.java)
            startActivity(addAddressIntent)
        }
        binding.commonTitleBar.tvTitle.text = "Place Order Inquiry"
        binding.commonTitleBar.backIcon.setOnClickListener { finish() }

        binding.btnPlaceOrder.setOnClickListener {
            if(isValidated()){
                val orderPlaceBody = OrderPlaceBody()
                orderPlaceBody.addressId = addressIdActivity
                orderPlaceBody.userId =  AppSession(this@CartAddressActivity).getUser()?.id

                val productsIds = intent.getIntegerArrayListExtra("PRODUCT_IDS")
                if (productsIds != null) {
                    for (ids in productsIds){
                        orderPlaceBody.productId = ids
                        viewModel.placeOrder(orderPlaceBody)

                    }
                }
                // Will not call as multiple calls will happen

            }
        }




    }

    private fun isValidated(): Boolean {
        if(addressIdActivity==-1){
            showToast("Please select an address to continue")
            return false
        }else{

            return true
        }



    }
}