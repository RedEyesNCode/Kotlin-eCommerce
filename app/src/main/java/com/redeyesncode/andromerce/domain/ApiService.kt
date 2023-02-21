package com.redeyesncode.andromerce.domain

import com.redeyesncode.andromerce.base.CommonResponseModel
import com.redeyesncode.andromerce.data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    
    @POST("merce/user/loginUser")
    fun loginUserEmail(@Body hashMap: HashMap<String, String>):Call<LoginUserResponse>


    @POST("merce/user/checkUser")
    fun checkUserByNumber(@Body hashMap: HashMap<String, String>):Call<CommonResponseModel>

    @POST("merce/user/forgotPassword")
    fun forgotPassword(@Body hashMap: HashMap<String, String>):Call<ForgotPasswordResponse>

    @POST("merce/user/signupUser")
    fun signupUser(@Body signupUserBody: SignupUserBody):Call<CommonResponseModel>

    @POST("merce/user/addUserAddress")
    fun addUserAddrress(@Body addUserAddressBody: AddAddressBody):Call<CommonResponseModel>


    @POST("merce/user/getUserAddresss")
    fun getUserAddress(@Body hashMap:HashMap<String,String>):Call<UserAddressResponseModel>


    @POST("merce/product/getAllProducts")
    fun getAllProducts():Call<GetAllProductsResponseModel>


    @GET("merce/product/getAllCategory")
    fun getAllCategory():Call<CategoryResponseModel>

    @GET("merce/product/getAllBanners")
    fun getAllBanners():Call<BannersResponseModel>

    @GET("merce/product/getAllSubCategory")
    fun getAllSubCategory():Call<AllSubCategoryResponse>


    @POST("merce/product/getProductDetail")
    fun getProductDetail(@Body hashMap: HashMap<String, String>):Call<ProductDetailResponseModel>

    @POST("merce/product/getProductImages")
    fun getProductImage(@Body hashMap: HashMap<String, String>):Call<ProductImagesResponse>


    @POST("merce/product/getProductsByCategory")
    fun getProductsByCategory():Call<CategoryProductsResponse>




    @GET("merce/product/getPopularProducts")
    fun getPopularProducts():Call<PopularProductResponse>

    @POST("merce/orders/place-order")
    fun  getAddressDetails(@Body orderPlaceBody:OrderPlaceBody):Call<CommonResponseModel>




}