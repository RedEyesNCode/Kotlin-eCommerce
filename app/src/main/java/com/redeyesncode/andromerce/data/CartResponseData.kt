package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class CartResponseData( @SerializedName("status"  ) var status  : String?         = null,
                             @SerializedName("code"    ) var code    : Int?            = null,
                             @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf(),
                             @SerializedName("message" ) var message : String?         = null
) {
    data class Product (

        @SerializedName("id"             ) var id            : Int?    = null,
        @SerializedName("productName"    ) var productName   : String? = null,
        @SerializedName("description"    ) var description   : String? = null,
        @SerializedName("quantity"       ) var quantity      : Int?    = null,
        @SerializedName("maxPrice"       ) var maxPrice      : String? = null,
        @SerializedName("sellingPrice"   ) var sellingPrice  : String? = null,
        @SerializedName("tags"           ) var tags          : String? = null,
        @SerializedName("category_id"    ) var categoryId    : Int?    = null,
        @SerializedName("subcategory_id" ) var subcategoryId : Int?    = null,
        @SerializedName("sku"            ) var sku           : String? = null

    )
    data class Data (

        @SerializedName("cartId"    ) var cartId    : Int?     = null,
        @SerializedName("userID"    ) var userID    : Int?     = null,
        @SerializedName("product"   ) var product   : Product? = Product(),
        @SerializedName("productId" ) var productId : Int?     = null,
        @SerializedName("quantity"  ) var quantity  : Int?     = null,
        @SerializedName("cartTotal" ) var cartTotal : Int?     = null,
        @SerializedName("image" ) var image : String?     = null

    )
}