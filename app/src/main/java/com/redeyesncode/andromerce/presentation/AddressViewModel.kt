package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.AddAddressBody
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.data.UpdateAddressBody
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AddressViewModel():ViewModel() {
    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _userAddressResponse = MutableLiveData<UserAddressResponseModel>()
    val userAddressResponse :LiveData<UserAddressResponseModel> = _userAddressResponse

    private val _commonStatusResponse = MutableLiveData<CommonResponseModel>()
    val commonStatusResponse :LiveData<CommonResponseModel> = _commonStatusResponse




    var mainRepo: MainRepository = MainRepository()


    fun addAddress(addAddressBody: AddAddressBody) = viewModelScope.launch {
        addAddressCouroutine(addAddressBody)


    }

    private suspend fun addAddressCouroutine(addAddressBody: AddAddressBody) {
        try {

            val response = mainRepo.addAddress(addAddressBody)
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


    fun deleteAddress(hashMap: HashMap<String, String>) = viewModelScope.launch {
        deleteAddressCouroutine(hashMap)


    }

    private suspend fun deleteAddressCouroutine(hashMap: java.util.HashMap<String, String>) {
        try {

            val response = mainRepo.deleteUserAddress(hashMap)
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


    fun updateAddress(updateAddressBody: UpdateAddressBody) = viewModelScope.launch {
        updateAddressCoroutine(updateAddressBody)


    }

    private suspend fun updateAddressCoroutine(updateAddressBody: UpdateAddressBody) {
        try {

            val response = mainRepo.updateUserAddress(updateAddressBody)
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