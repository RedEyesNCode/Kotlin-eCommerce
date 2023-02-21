package com.redeyesncode.andromerce.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import com.redeyesncode.andromerce.databinding.ActivityViewAddressBinding
import com.redeyesncode.andromerce.presentation.AddressViewModel
import com.redeyesncode.andromerce.ui.adapters.AddressAdapter
import com.redeyesncode.andromerce.utils.AppSession

class ViewAddressActivity : BaseActivity(), AddressAdapter.onEventAddress {

    lateinit var binding:ActivityViewAddressBinding
    lateinit var addressViewModel: AddressViewModel
    override fun onDeleteClick(position: Int, addressId: Int) {
        val alertDialog = AlertDialog.Builder(this@ViewAddressActivity)
        alertDialog.setTitle("Delete this address ?")
        alertDialog.setCancelable(true)
        alertDialog.setPositiveButton("Yes",{dialog, which ->
            run {
                val hashMap = HashMap<String,String>()
                hashMap.put("address_id",addressId.toString())
                showLoader()
                addressViewModel.deleteAddress(hashMap)
                initialApiCall()
            }
        })
        alertDialog.setNegativeButton("CANCEL",null)
        alertDialog.create()

        alertDialog.show()


    }

    override fun onEditClick(position: Int, data: UserAddressResponseModel.Data) {
        showToast("On Edit")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAddressBinding.inflate(layoutInflater)
        initClicks()
        setupViewModel()
        attachObservers()
        initialApiCall()
        setContentView(binding.root)
    }

    private fun initialApiCall() {
        val hashMap = HashMap<String,String>()
        showLoader()
        hashMap.put("user_id",AppSession(this@ViewAddressActivity).getUser()?.id.toString())
        addressViewModel.getUserAddress(hashMap)



    }

    private fun setupAddressAdapter(it: UserAddressResponseModel?) {
        binding.recvAddress.adapter = AddressAdapter(this@ViewAddressActivity,this,it!!.data)
        binding.recvAddress.layoutManager = LinearLayoutManager(this@ViewAddressActivity,LinearLayoutManager.VERTICAL,false)
    }

    private fun attachObservers() {
        addressViewModel.isFailed.observe((this)){
            hideLoader()

            if(it!=null){
                showToast(it)
            }
        }
        addressViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        addressViewModel.userAddressResponse.observe((this)){
            hideLoader()
            // update the recyclerview
            if(it.data.size!=0){
                setupAddressAdapter(it)
            }else {
                showDialog("Record Not Found !","Important Alert !")
            }


        }

    }

    private fun setupViewModel() {
        addressViewModel = AddressViewModel()
        addressViewModel = ViewModelProvider(this).get(AddressViewModel::class.java)
        binding.viewmodel = addressViewModel

    }

    private fun initClicks() {



    }
}