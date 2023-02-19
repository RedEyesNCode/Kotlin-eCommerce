package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class BannersResponseModel(  @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
){
    data class Data (

        @SerializedName("id"         ) var id         : Int?    = null,
        @SerializedName("bannerLink" ) var bannerLink : String? = null

    )
}
