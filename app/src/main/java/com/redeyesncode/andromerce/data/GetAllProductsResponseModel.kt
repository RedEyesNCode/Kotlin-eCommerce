package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class GetAllProductsResponseModel(  @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()
){
    data class Data (

        @SerializedName("id"             ) var id            : Int?    = null,
        @SerializedName("productName"           ) var name          : String? = null,
        @SerializedName("description"    ) var description   : String? = null,
        @SerializedName("category_id"    ) var categoryId    : Int?    = null,
        @SerializedName("subCategory_id" ) var subCategoryId : Int?    = null,
        @SerializedName("inventory_id"   ) var inventoryId   : Int?    = null,
        @SerializedName("categoryName"   ) var categoryName  : String? = null,
        @SerializedName("price"          ) var price         : String? = null,
        @SerializedName("discount_id"    ) var discountId    : Int?    = null,
        @SerializedName("sku"            ) var sku           : String? = null

    )
}
