package com.redeyesncode.andromerce.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.SignupUserBody
import com.redeyesncode.andromerce.utils.Constant
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SignupViewModel(): ViewModel() {

    private val _isFailed = MutableLiveData<String>()
    val isFailed: LiveData<String> = _isFailed
    private val _isLoading = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isLoading

    private val _commonResponse = MutableLiveData<CommonResponseModel>()
    val commonResponse :LiveData<CommonResponseModel> = _commonResponse


    private val _commonResponseCheckUser = MutableLiveData<CommonResponseModel>()
    val commonResponseModelCheckUser :LiveData<CommonResponseModel> = _commonResponseCheckUser


    var mainRepo: MainRepository = MainRepository()


    fun checkUserForOtp(hashMap: HashMap<String,String>) = viewModelScope.launch {
        checkUserOtpCoroutine(hashMap)

    }

    private suspend fun checkUserOtpCoroutine(hashMap: java.util.HashMap<String, String>) {
        try {

            val response = mainRepo.checkUserByNumber(hashMap)
            _isLoading.value = true
            response.enqueue(object : Callback<CommonResponseModel> {

                override fun onResponse(
                    call: Call<CommonResponseModel>,
                    response: Response<CommonResponseModel>
                ) {
                    _isLoading.value = false
                    if (response.code() == 200 || response.code()==400) {
                        _commonResponseCheckUser.postValue(response.body())
                    } else {
                        if(response.code()==500){
                            _isFailed.value = "User already exists try with different number."
                        }else{
                            _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"

                        }
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

    fun signupUser(signupBody:SignupUserBody) = viewModelScope.launch {
        signupUserCoroutine(signupBody)


    }

    private suspend fun signupUserCoroutine(signupBody: SignupUserBody) {
        try {

            val response = mainRepo.signupUser(signupBody)
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
                        _isFailed.value = "${Constant.OOPS_SW} ${response.code()}"
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


}