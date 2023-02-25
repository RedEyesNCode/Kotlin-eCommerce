package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.data.GetAllProductsResponseModel
import com.redeyesncode.andromerce.data.PopularProductResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchViewModel():ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading


    private val _searchProductResponse = MutableLiveData<GetAllProductsResponseModel>()
    val searchProductResponse :LiveData<GetAllProductsResponseModel> = _searchProductResponse

    var mainRepo: MainRepository = MainRepository()


    fun searchProduct(hashMap:HashMap<String,String>) = viewModelScope.launch {

        searchProductCoroutine(hashMap)

    }

    private suspend fun searchProductCoroutine(hashMap: java.util.HashMap<String, String>) {

        try {

            val response = mainRepo.searchProduct(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<GetAllProductsResponseModel> {

                override fun onResponse(
                    call: Call<GetAllProductsResponseModel>,
                    response: Response<GetAllProductsResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _searchProductResponse.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !. ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<GetAllProductsResponseModel>, t: Throwable) {
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