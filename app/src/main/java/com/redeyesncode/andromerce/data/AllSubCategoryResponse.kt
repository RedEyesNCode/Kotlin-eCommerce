package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class AllSubCategoryResponse(  @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
){

    data class Data (

        @SerializedName("subCategoryId" ) var subCategoryId : Int?    = null,
        @SerializedName("name"          ) var name          : String? = null,
        @SerializedName("description"   ) var description   : String? = null,
        @SerializedName("image"         ) var image         : String? = null

    )
}
