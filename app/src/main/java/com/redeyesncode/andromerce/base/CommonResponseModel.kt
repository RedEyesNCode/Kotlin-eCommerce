package com.redeyesncode.andromerce.base

import com.google.gson.annotations.SerializedName

data class CommonResponseModel(@SerializedName("status"  ) var status  : String? = null,
                               @SerializedName("code"    ) var code    : Int?    = null,
                               @SerializedName("message" ) var message : String? = null)
