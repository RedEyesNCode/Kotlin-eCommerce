package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DashboardViewModel(): ViewModel() {

    private val _commonResponse = MutableLiveData<CommonResponseModel>()
    val commonResponse: LiveData<CommonResponseModel> = _commonResponse

    private val _categoryResponseModel = MutableLiveData<CategoryResponseModel>()
    val categoryResponseModel: LiveData<CategoryResponseModel> = _categoryResponseModel


    private val _productsResponseModel = MutableLiveData<GetAllProductsResponseModel>()
    val productsResponseModel: LiveData<GetAllProductsResponseModel> = _productsResponseModel

    private val _bannerResponseModel = MutableLiveData<BannersResponseModel>()
    val bannersResponseModel :LiveData<BannersResponseModel> = _bannerResponseModel

    private val _subCateogryResponseModel = MutableLiveData<AllSubCategoryResponse>()
    val subCategoryResponseModel :LiveData<AllSubCategoryResponse> = _subCateogryResponseModel

    private val _popularProductsResponseModel = MutableLiveData<PopularProductResponse> ()

    val popularProductResponse :LiveData<PopularProductResponse> = _popularProductsResponseModel;





    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    var mainRepo: MainRepository = MainRepository()




    fun getAllPopularProducts() = viewModelScope.launch {

        getAllPopularCouroutine()
    }

    private suspend fun getAllPopularCouroutine() {
        try {

            val response = mainRepo.getAllPopularProducts()
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


    fun getAllSubCategory() = viewModelScope.launch {

        getAllSubCategoryCoroutine()


    }

    private suspend fun getAllSubCategoryCoroutine() {

        try {

            val response = mainRepo.getAllSubcategory()
            _isLoading.value = true
            response.enqueue(object : Callback<AllSubCategoryResponse> {

                override fun onResponse(
                    call: Call<AllSubCategoryResponse>,
                    response: Response<AllSubCategoryResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _subCateogryResponseModel.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<AllSubCategoryResponse>, t: Throwable) {
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

    fun getAllBanners() = viewModelScope.launch {
        getAllBannerCouroutine()

    }

    private suspend fun getAllBannerCouroutine() {
        try {

            val response = mainRepo.getAllBanners()
            _isLoading.value = true
            response.enqueue(object : Callback<BannersResponseModel> {

                override fun onResponse(
                    call: Call<BannersResponseModel>,
                    response: Response<BannersResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _bannerResponseModel.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
                    }
                }

                override fun onFailure(call: Call<BannersResponseModel>, t: Throwable) {
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


    fun getAllProducts() = viewModelScope.launch {
        getAllProductsCoroutine()

    }

    private suspend fun getAllProductsCoroutine() {
        try {

            val response = mainRepo.getAllProducts()
            _isLoading.value = true
            response.enqueue(object : Callback<GetAllProductsResponseModel> {

                override fun onResponse(
                    call: Call<GetAllProductsResponseModel>,
                    response: Response<GetAllProductsResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _productsResponseModel.postValue(response.body())
                    } else {
                        _isFailed.value = "Record Not Found !."
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
}