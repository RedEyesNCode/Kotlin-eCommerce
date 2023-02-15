package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.CategoryResponseModel
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


    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    var mainRepo: MainRepository = MainRepository()

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