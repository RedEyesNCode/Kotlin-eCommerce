package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class SignupUserBody(@SerializedName("username"   ) var username  : String? = null,
                          @SerializedName("password"   ) var password  : String? = null,
                          @SerializedName("first_name" ) var firstName : String? = null,
                          @SerializedName("last_name"  ) var lastName  : String? = null,
                          @SerializedName("telephone"  ) var telephone : String? = null)
