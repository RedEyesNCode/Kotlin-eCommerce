package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.AddAddressBody
import com.redeyesncode.andromerce.data.OrderPlaceBody
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class OrderViewModel() :ViewModel() {
    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _commonStatusResponse = MutableLiveData<CommonResponseModel>()
    val commonStatusResponse :LiveData<CommonResponseModel> = _commonStatusResponse
    var mainRepo: MainRepository = MainRepository()
    private val _userAddressResponse = MutableLiveData<UserAddressResponseModel>()
    val userAddressResponse :LiveData<UserAddressResponseModel> = _userAddressResponse


    fun placeOrder(orderPlaceBody: OrderPlaceBody) = viewModelScope.launch {
        placeOrderCoroutine(orderPlaceBody)

    }

    private suspend fun placeOrderCoroutine(orderPlaceBody: OrderPlaceBody) {
        try {

            val response = mainRepo.placeOrder(orderPlaceBody)
            _isLoading.value = true
            response.enqueue(object : Callback<CommonResponseModel> {

                override fun onResponse(
                    call: Call<CommonResponseModel>,
                    response: Response<CommonResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _commonStatusResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<CommonResponseModel>, t: Throwable) {
                    _isFailed.value = t.message
                }
            })
        } catch (t: Throwable) {

            when (t) {
                is IOException -> {
                    _isFailed.value = "IO Exception"
                }
                else -> {
                    _isFailed.value = "Exception." + t.message

                    Log.i("RETROFIT", t.message!!)
                }


            }

        }




    }

    fun getUserAddress(hashMap: HashMap<String,String>) = viewModelScope.launch {
        getUserAddressCouroutine(hashMap)
    }

    private suspend fun getUserAddressCouroutine(hashMap: java.util.HashMap<String, String>) {
        try {

            val response = mainRepo.getUserAddress(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<UserAddressResponseModel> {

                override fun onResponse(
                    call: Call<UserAddressResponseModel>,
                    response: Response<UserAddressResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _userAddressResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<UserAddressResponseModel>, t: Throwable) {
                    _isFailed.value = t.message
                }
            })
        } catch (t: Throwable) {

            when (t) {
                is IOException -> {
                    _isFailed.value = "IO Exception"
                }
                else -> {
                    _isFailed.value = "Exception." + t.message

                    Log.i("RETROFIT", t.message!!)
                }


            }

        }

    }


}