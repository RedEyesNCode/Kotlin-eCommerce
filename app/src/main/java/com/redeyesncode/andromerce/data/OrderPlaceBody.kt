package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class OrderPlaceBody(@SerializedName("user_id"    ) var userId    : Int? = null,
                          @SerializedName("address_id" ) var addressId : Int? = null,
                          @SerializedName("product_id" ) var productId : Int? = null,
                          @SerializedName("quantity"   ) var quantity  : Int? = null)
