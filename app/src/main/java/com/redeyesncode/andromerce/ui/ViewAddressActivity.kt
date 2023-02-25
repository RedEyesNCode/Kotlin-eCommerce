package com.redeyesncode.andromerce.ui

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.AddAddressBody
import com.redeyesncode.andromerce.data.UpdateAddressBody
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import com.redeyesncode.andromerce.databinding.ActivityViewAddressBinding
import com.redeyesncode.andromerce.databinding.BottomSheetAddressBinding
import com.redeyesncode.andromerce.presentation.AddressViewModel
import com.redeyesncode.andromerce.ui.adapters.AddressAdapter
import com.redeyesncode.andromerce.utils.AppSession

class ViewAddressActivity : BaseActivity(), AddressAdapter.onEventAddress {

    lateinit var binding:ActivityViewAddressBinding
    lateinit var addressViewModel: AddressViewModel

    lateinit var bottomSheetDialog:BottomSheetDialog





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
        setupBottomSheetDialog(true,data)
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

    private fun setupBottomSheetDialog(isUpdate: Boolean, address: UserAddressResponseModel.Data?) {
        val bottomSheetBinding = BottomSheetAddressBinding.inflate(LayoutInflater.from(this@ViewAddressActivity))
        bottomSheetDialog = BottomSheetDialog(this@ViewAddressActivity,R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
        if(isUpdate){
            bottomSheetBinding.btnAddress.setTvButtonText("Update Address")
            bottomSheetBinding.edtAddressLineOne.setText(address?.addressLine1)
            bottomSheetBinding.edtAddressLineTwo.setText(address?.addressLine2)
            bottomSheetBinding.edtPostalCode.setText(address?.postalCode)
            bottomSheetBinding.edtCity.setText(address?.city)
            bottomSheetBinding.edtTelephone.setText(address?.telephone)
            bottomSheetBinding.btnAddress.setOnClickListener {
                if(bottomSheetBinding.edtAddressLineOne.text.toString().isEmpty()){
                    showToast("Please enter address line one.")
                }else if(bottomSheetBinding.edtAddressLineTwo.text.toString().isEmpty()){
                    showToast("Plesae enter address line two")

                }else if(bottomSheetBinding.edtPostalCode.text.toString().isEmpty()){
                    showToast("Please enter postal code.")

                }else if(bottomSheetBinding.edtCity.text.toString().isEmpty()){
                    showToast("Please enter city")
                }else if(bottomSheetBinding.edtTelephone.text.toString().isEmpty()){
                    showToast("Please enter telephone.")
                }else{
                    val updateAddressBody = UpdateAddressBody()
                    updateAddressBody.id = address?.id
                    updateAddressBody.userId = address?.userId
                    updateAddressBody.addressLine1 = bottomSheetBinding.edtAddressLineOne.text.toString()
                    updateAddressBody.addressLine2 = bottomSheetBinding.edtAddressLineTwo.text.toString()
                    updateAddressBody.city = bottomSheetBinding.edtCity.text.toString()
                    updateAddressBody.telephone = bottomSheetBinding.edtTelephone.text.toString().trim()
                    updateAddressBody.country = "INDIA"
                    updateAddressBody.postalCode = bottomSheetBinding.edtPostalCode.text.toString().trim()
                    addressViewModel.updateAddress(updateAddressBody)
                    bottomSheetBinding.btnAddress.showProgress("Updating")
                    Handler().postDelayed(Runnable {
                        bottomSheetBinding.btnAddress.hideProgress("Update Address")
                        bottomSheetDialog.dismiss()
                        initialApiCall()

                    },4000)


                }


            }


        }else{
            bottomSheetBinding.btnAddress.setTvButtonText("Add New Address")


            bottomSheetBinding.btnAddress.setOnClickListener {
                if(bottomSheetBinding.edtAddressLineOne.text.toString().isEmpty()){
                    showToast("Please enter address line one.")
                }else if(bottomSheetBinding.edtAddressLineTwo.text.toString().isEmpty()){
                    showToast("Plesae enter address line two")

                }else if(bottomSheetBinding.edtPostalCode.text.toString().isEmpty()){
                    showToast("Please enter postal code.")

                }else if(bottomSheetBinding.edtCity.text.toString().isEmpty()){
                    showToast("Please enter city")
                }else if(bottomSheetBinding.edtTelephone.text.toString().isEmpty()){
                    showToast("Please enter telephone.")
                }else{
                    val addAddressBody = AddAddressBody()
                    addAddressBody.userId = AppSession(this@ViewAddressActivity).getUser()?.id?.toInt()
                    addAddressBody.addressLine1 = bottomSheetBinding.edtAddressLineOne.text.toString()
                    addAddressBody.addressLine2 = bottomSheetBinding.edtAddressLineTwo.text.toString()
                    addAddressBody.city = bottomSheetBinding.edtCity.text.toString()
                    addAddressBody.telephone = bottomSheetBinding.edtTelephone.text.toString().trim()
                    addAddressBody.country = "INDIA"
                    addAddressBody.postalCode = bottomSheetBinding.edtPostalCode.text.toString().trim()
                    addressViewModel.addAddress(addAddressBody)
                    bottomSheetBinding.btnAddress.showProgress("Updating")
                    Handler().postDelayed(Runnable {
                        bottomSheetBinding.btnAddress.hideProgress("Update Address")
                        bottomSheetDialog.dismiss()
                        initialApiCall()

                    },4000)


                }


            }
        }











    }

    private fun initialApiCall() {
        val hashMap = HashMap<String,String>()
        showLoader()
        hashMap.put("user_id",AppSession(this@ViewAddressActivity).getUser()?.id.toString())
        addressViewModel.getUserAddress(hashMap)



    }

    private fun setupAddressAdapter(it: UserAddressResponseModel?) {
        binding.recvAddress.adapter = AddressAdapter(this@ViewAddressActivity,this,it!!.data,true)
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

    override fun onSelectAddress(position: Int, addressId: Int) {
        showLog("onSelectAddress")
    }

    private fun initClicks() {
        binding.fabAddAddress.setOnClickListener {
            setupBottomSheetDialog(false,null)
        }
        binding.commonTitleBar.tvTitle.text = "My Saved Address"
        binding.commonTitleBar.backIcon.setOnClickListener {
            finish()
        }


    }
}