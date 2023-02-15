package com.redeyesncode.andromerce.domain

import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    @POST("merce/user/signupUser")
    fun signupUser(@Body signupUserBody: SignupUserBody):Call<CommonResponseModel>

    @POST("merce/user/addUserAddress")
    fun addUserAddrress(@Body addUserAddressBody: AddAddressBody):Call<CommonResponseModel>


    @POST("merce/user/getUserAddresss")
    fun getUserAddress(@Body hashMap:HashMap<String,String>):Call<UserAddressResponseModel>


    @POST("merce/user/getAllProducts")
    fun getAllProducts():Call<GetAllProductsResponseModel>


    @POST("merce/user/getAllCategory")
    fun getAllCategory():Call<CategoryResponseModel>

    @POST("merce/user/getProductsByCategory")
    fun getProductsByCategory():Call<CategoryProductsResponse>



    @POST("merce/user/getProductDetail")
    fun getProductDetails():Call<ProductDetailResponseModel>


    @POST("merce/orders/place-order")
    fun  getAddressDetails(@Body orderPlaceBody:OrderPlaceBody):Call<CommonResponseModel>

}