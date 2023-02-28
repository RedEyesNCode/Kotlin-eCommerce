package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.data.CategoryProductsResponse
import com.redeyesncode.andromerce.data.CategoryResponseModel
import com.redeyesncode.andromerce.data.PopularProductResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AllProductsViewModel():ViewModel() {
    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    var mainRepo: MainRepository = MainRepository()

    private val _popularProductsResponseModel = MutableLiveData<PopularProductResponse> ()
    val popularProductResponse :LiveData<PopularProductResponse> = _popularProductsResponseModel;

    private val _categoryResponseModel = MutableLiveData<CategoryResponseModel>()
    val categoryResponseModel :LiveData<CategoryResponseModel> = _categoryResponseModel

    private val _categoryProductsResponseModel = MutableLiveData<PopularProductResponse>()
    val categoryProductsResponseModel :LiveData<PopularProductResponse> = _categoryProductsResponseModel

    fun getProductByCategory(hashMap: HashMap<String,String>) = viewModelScope.launch {

        getProductsForCategoryKTX(hashMap)

    }

    fun getProductBySubCategory(hashMap: HashMap<String, String>) = viewModelScope.launch {
        try {

            val response = mainRepo.getProductForSubCategory(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<PopularProductResponse> {

                override fun onResponse(
                    call: Call<PopularProductResponse>,
                    response: Response<PopularProductResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _categoryProductsResponseModel.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<PopularProductResponse>, t: Throwable) {
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

    private suspend fun getProductsForCategoryKTX(hashMap: java.util.HashMap<String, String>) {
        try {

            val response = mainRepo.getAllProductForCategory(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<PopularProductResponse> {

                override fun onResponse(
                    call: Call<PopularProductResponse>,
                    response: Response<PopularProductResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _categoryProductsResponseModel.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<PopularProductResponse>, t: Throwable) {
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

    fun getAllCategory() = viewModelScope.launch {
        getAllCategoryCouroutine()

    }

    private suspend fun getAllCategoryCouroutine() {

        try {

            val response = mainRepo.getAllCategory()
            _isLoading.value = true
            response.enqueue(object : Callback<CategoryResponseModel> {

                override fun onResponse(
                    call: Call<CategoryResponseModel>,
                    response: Response<CategoryResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _categoryResponseModel.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<CategoryResponseModel>, t: Throwable) {
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


    fun getAllProductsDetail() = viewModelScope.launch {

        getAllProductsDetailCoroutine()

    }

    private suspend fun getAllProductsDetailCoroutine() {

        try {

            val response = mainRepo.getAllProductDetails()
            _isLoading.value = true
            response.enqueue(object : Callback<PopularProductResponse> {

                override fun onResponse(
                    call: Call<PopularProductResponse>,
                    response: Response<PopularProductResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _popularProductsResponseModel.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<PopularProductResponse>, t: Throwable) {
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