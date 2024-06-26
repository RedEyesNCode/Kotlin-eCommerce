package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class CategoryResponseModel(  @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
){
    data class Data (

        @SerializedName("category_id"          ) var id          : Int?    = null,
        @SerializedName("name"        ) var name        : String? = null,
        @SerializedName("description" ) var description : String? = null,
        @SerializedName("image" ) var image : String? = null

    )
}
