package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.data.CartResponseData
import com.redeyesncode.andromerce.data.ProductDetailResponseModel
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProductDetailViewModel():ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _productDetailResponseModel = MutableLiveData<ProductDetailResponseModel>()

    val productDetailResponse :LiveData<ProductDetailResponseModel> = _productDetailResponseModel

    private val _cartResponse = MutableLiveData<CartResponseData>()
    val cartResponse :LiveData<CartResponseData> = _cartResponse


    fun addToCart(hashMap: HashMap<String,String>) = viewModelScope.launch {

        addToCartCoroutine(hashMap)

    }

    private suspend fun addToCartCoroutine(hashMap: java.util.HashMap<String, String>) {

        try {

            val response = mainRepo.addToCart(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<CartResponseData> {

                override fun onResponse(
                    call: Call<CartResponseData>,
                    response: Response<CartResponseData>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _cartResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<CartResponseData>, t: Throwable) {
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


    var mainRepo: MainRepository = MainRepository()
    private val _userAddressResponse = MutableLiveData<UserAddressResponseModel>()
    val userAddressResponse :LiveData<UserAddressResponseModel> = _userAddressResponse

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


    fun getProductDetail(hashMap: HashMap<String,String>) = viewModelScope.launch {

        getProductDetailCoroutine(hashMap)

    }

    private suspend fun getProductDetailCoroutine(hashMap: java.util.HashMap<String, String>) {
        try {

            val response = mainRepo.getProductDetail(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<ProductDetailResponseModel> {

                override fun onResponse(
                    call: Call<ProductDetailResponseModel>,
                    response: Response<ProductDetailResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _productDetailResponseModel.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<ProductDetailResponseModel>, t: Throwable) {
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