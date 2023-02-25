package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.LoginUserResponse
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.utils.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginViewModel() :ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    var mainRepo: MainRepository = MainRepository()

    private val _commonResponse = MutableLiveData<CommonResponseModel>()
    val commonResponse: LiveData<CommonResponseModel> = _commonResponse

    private val _loginResponse = MutableLiveData<LoginUserResponse>()
    val loginResponse:LiveData<LoginUserResponse> = _loginResponse



    fun insertFcmToken(map:HashMap<String,String>) = viewModelScope.launch {

        insertFcmTokenCoroutine(map)


    }

    private suspend fun insertFcmTokenCoroutine(map: java.util.HashMap<String, String>) {

        try {

            val response = mainRepo.insertFcm(map)
            _isLoading.value = true
            response.enqueue(object : Callback<CommonResponseModel> {

                override fun onResponse(
                    call: Call<CommonResponseModel>,
                    response: Response<CommonResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                       _isLoading.value = false
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

    fun loginUser(hashMap: HashMap<String,String>) = viewModelScope.launch {

        loginUserCoroutine(hashMap)

    }




    private suspend fun loginUserCoroutine(hashMap: java.util.HashMap<String, String>) {
        try {

            val response = mainRepo.loginUser(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<LoginUserResponse> {

                override fun onResponse(
                    call: Call<LoginUserResponse>,
                    response: Response<LoginUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200) {
                        _loginResponse.postValue(response.body())
                    } else {
                        _isFailed.value = Constant.OOPS_SW +"Code : ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
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