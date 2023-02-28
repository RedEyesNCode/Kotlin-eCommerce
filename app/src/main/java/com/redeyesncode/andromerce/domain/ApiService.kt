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

    @GET("merce/product/getAllProductsDetails")
    fun getAllProductDetails():Call<PopularProductResponse>


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
    fun getProductsByCategory(@Body map:HashMap<String,String>):Call<PopularProductResponse>

    @POST("merce/product/getProductsBySubCategory")
    fun getProductBySubCategory(@Body map: HashMap<String, String>):Call<PopularProductResponse>


    @POST("merce/product/searchProduct")
    fun searchProduct (@Body hashMap:HashMap<String,String>):Call<GetAllProductsResponseModel>



    @GET("merce/product/getPopularProducts")
    fun getPopularProducts():Call<PopularProductResponse>

    @POST("merce/orders/place-order")
    fun  placeOrder(@Body orderPlaceBody:OrderPlaceBody):Call<CommonResponseModel>

    @POST("merce/user/updateAddress")
    fun updateUserAddress(@Body updateAddressBody: UpdateAddressBody): Call<CommonResponseModel>

    @POST("merce/user/deleteAddress")
    fun deleteAddress(@Body hashMap: HashMap<String, String>): Call<CommonResponseModel>


    @POST("merce/notifications/fcm")
    fun insertFcmToken(@Body hashMap: HashMap<String, String>):Call<CommonResponseModel>

    // Cart api's are placed here.


    @POST("merce/cart/user")
    fun getCart(@Body hashMap: HashMap<String, String>):Call<CartResponseData>

    @POST("merce/cart/add-to-cart") // this api will perform both the operation add to Cart and Update Cart.
    fun addToCart(@Body hashMap: HashMap<String, String>):Call<CartResponseData>


    @POST("merce/cart/delete-cart-item")
    fun deleteCart(@Body hashMap: HashMap<String, String>):Call<CommonResponseModel>

}