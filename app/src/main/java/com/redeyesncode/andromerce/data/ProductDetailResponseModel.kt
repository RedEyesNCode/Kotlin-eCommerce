package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class ProductDetailResponseModel(
    @SerializedName("status"       ) var status       : String?          = null,
    @SerializedName("code"         ) var code         : Int?             = null,
    @SerializedName("message"      ) var message      : String?          = null,
    @SerializedName("productTable" ) var productTable : ProductTable?    = ProductTable(),
    @SerializedName("inventory"    ) var inventory    : Inventory?       = Inventory(),
    @SerializedName("media"        ) var media        : ArrayList<Media> = arrayListOf()) {


    data class ProductTable (

        @SerializedName("id"             ) var id            : Int?    = null,
        @SerializedName("name"           ) var name          : String? = null,
        @SerializedName("description"    ) var description   : String? = null,
        @SerializedName("category_id"    ) var categoryId    : Int?    = null,
        @SerializedName("subCategory_id" ) var subCategoryId : Int?    = null,
        @SerializedName("inventory_id"   ) var inventoryId   : Int?    = null,
        @SerializedName("categoryName"   ) var categoryName  : String? = null,
        @SerializedName("price"          ) var price         : String? = null,
        @SerializedName("discount_id"    ) var discountId    : Int?    = null,
        @SerializedName("sku"            ) var sku           : String? = null

    )
    data class Inventory (

        @SerializedName("id"         ) var id        : Int?    = null,
        @SerializedName("quantity"   ) var quantity  : String? = null,
        @SerializedName("product_id" ) var productId : Int?    = null

    )
    data class Media (

        @SerializedName("id"         ) var id        : Int?    = null,
        @SerializedName("link"       ) var link      : String? = null,
        @SerializedName("product_id" ) var productId : Int?    = null

    )
}