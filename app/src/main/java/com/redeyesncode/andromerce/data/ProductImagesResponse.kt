package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class ProductImagesResponse(
    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()){
    data class Data (

        @SerializedName("id"         ) var id        : Int?    = null,
        @SerializedName("link"       ) var link      : String? = null,
        @SerializedName("product_id" ) var productId : Int?    = null

    )
}

