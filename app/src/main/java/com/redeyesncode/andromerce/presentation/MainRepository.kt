package com.redeyesncode.andromerce.presentation

import com.redeyesncode.andromerce.domain.AndroidClient
import java.util.HashMap

class MainRepository {
    suspend fun getAllCategory() = AndroidClient().apiInterface.getAllCategory()

    suspend fun getAllProducts() = AndroidClient().apiInterface.getAllProducts()


    suspend fun getAllSubcategory() = AndroidClient().apiInterface.getAllSubCategory()

    suspend fun getAllBanners() = AndroidClient().apiInterface.getAllBanners()
    suspend fun getAllProductImages(hashMap: HashMap<String, String>) = AndroidClient().apiInterface.getProductImage(hashMap)
    suspend  fun getProductDetail(hashMap: java.util.HashMap<String,String>) = AndroidClient().apiInterface.getProductDetail(hashMap)
}