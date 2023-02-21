package com.redeyesncode.andromerce.presentation

import com.redeyesncode.andromerce.data.AddAddressBody
import com.redeyesncode.andromerce.data.SignupUserBody
import com.redeyesncode.andromerce.data.UpdateAddressBody
import com.redeyesncode.andromerce.domain.AndroidClient
import retrofit2.http.Body
import java.util.HashMap

class MainRepository {
    suspend fun getAllCategory() = AndroidClient().apiInterface.getAllCategory()

    suspend fun getAllProducts() = AndroidClient().apiInterface.getAllProducts()


    suspend fun getAllSubcategory() = AndroidClient().apiInterface.getAllSubCategory()

    suspend fun getAllBanners() = AndroidClient().apiInterface.getAllBanners()
    suspend fun getAllProductImages( hashMap: HashMap<String, String>) = AndroidClient().apiInterface.getProductImage(hashMap)

    suspend fun getAllPopularProducts() = AndroidClient().apiInterface.getPopularProducts()
    suspend  fun getProductDetail( hashMap: java.util.HashMap<String,String>) = AndroidClient().apiInterface.getProductDetail(hashMap)


    suspend fun signupUser( signupUserBody: SignupUserBody) = AndroidClient().apiInterface.signupUser(signupUserBody)
    suspend fun loginUser( hashMap: HashMap<String, String>) = AndroidClient().apiInterface.loginUserEmail(hashMap)

    suspend fun forgotPassword( hashMap: HashMap<String, String>) = AndroidClient().apiInterface.forgotPassword(hashMap)

    suspend fun checkUserByNumber( hashMap: HashMap<String, String>) = AndroidClient().apiInterface.checkUserByNumber(hashMap)


    suspend fun getUserAddress( hashMap: HashMap<String, String>) = AndroidClient().apiInterface.getUserAddress(hashMap)

    suspend fun updateUserAddress( updateAddressBody : UpdateAddressBody) = AndroidClient().apiInterface.updateUserAddress(updateAddressBody)

    suspend fun deleteUserAddress(hashMap: HashMap<String, String>) = AndroidClient().apiInterface.deleteAddress(hashMap)
    suspend fun addAddress(addAddressBody: AddAddressBody) = AndroidClient().apiInterface.addUserAddrress(addAddressBody)
}