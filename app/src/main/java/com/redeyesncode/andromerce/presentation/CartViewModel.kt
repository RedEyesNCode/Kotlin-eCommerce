package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.CartResponseData
import com.redeyesncode.andromerce.data.PopularProductResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CartViewModel() :ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    var mainRepo: MainRepository = MainRepository()

    private val _commonResponse = MutableLiveData<CommonResponseModel>()
    val commonResponse: LiveData<CommonResponseModel> = _commonResponse

    private val _cartResponse = MutableLiveData<CartResponseData>()
    val cartResponse :LiveData<CartResponseData> = _cartResponse



    fun deleteCart(hashMap: HashMap<String, String>) = viewModelScope.launch {

        deleteCartCoroutine(hashMap)

    }

    private suspend fun deleteCartCoroutine(hashMap: java.util.HashMap<String, String>) {

        try {

            val response = mainRepo.deleteCart(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<CommonResponseModel> {

                override fun onResponse(
                    call: Call<CommonResponseModel>,
                    response: Response<CommonResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _commonResponse.postValue(response.body())
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


    fun getCart(hashMap: HashMap<String,String>) = viewModelScope.launch {

        getCartCoroutine(hashMap)

    }

    private suspend fun getCartCoroutine(hashMap: java.util.HashMap<String, String>) {

        try {

            val response = mainRepo.getCart(hashMap)
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


}