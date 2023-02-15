package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class UserAddressResponseModel(  @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
){
    data class Data (

        @SerializedName("id"            ) var id           : Int?    = null,
        @SerializedName("user_id"       ) var userId       : Int?    = null,
        @SerializedName("address_line1" ) var addressLine1 : String? = null,
        @SerializedName("address_line2" ) var addressLine2 : String? = null,
        @SerializedName("city"          ) var city         : String? = null,
        @SerializedName("postal_code"   ) var postalCode   : String? = null,
        @SerializedName("country"       ) var country      : String? = null,
        @SerializedName("telephone"     ) var telephone    : String? = null,
        @SerializedName("mobile"        ) var mobile       : String? = null

    )
}
