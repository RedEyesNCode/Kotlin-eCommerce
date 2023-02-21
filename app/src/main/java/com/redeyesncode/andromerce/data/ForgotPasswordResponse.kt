package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(  @SerializedName("data" ) var data : Data? = Data()
){
    data class Data (

        @SerializedName("id"         ) var id        : Int?     = null,
        @SerializedName("password"   ) var password  : String?  = null,
        @SerializedName("first_name" ) var firstName : String?  = null,
        @SerializedName("last_name"  ) var lastName  : String?  = null,
        @SerializedName("email"      ) var email     : String?  = null,
        @SerializedName("telephone"  ) var telephone : String?  = null,
        @SerializedName("verified"   ) var verified  : Boolean? = null

    )
}
