package com.redeyesncode.andromerce.presentation

import com.redeyesncode.andromerce.domain.AndroidClient

class MainRepository {
    suspend fun getAllCategory() = AndroidClient().apiInterface.getAllCategory()

}