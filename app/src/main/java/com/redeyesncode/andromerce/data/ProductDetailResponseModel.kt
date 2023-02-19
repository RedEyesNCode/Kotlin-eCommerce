package com.redeyesncode.andromerce.data

import com.google.gson.annotations.SerializedName

data class ProductDetailResponseModel(
    @SerializedName("status"       ) var status       : String?          = null,
    @SerializedName("code"         ) var code         : Int?             = null,
    @SerializedName("message"      ) var message      : String?          = null,
    @SerializedName("productTable" ) var productTable : ProductTable?    = ProductTable(),
    @SerializedName("category"     ) var category     : Category?        = Category(),
    @SerializedName("subcategory"  ) var subcategory  : Subcategory?     = Subcategory(),
    @SerializedName("media"        ) var media        : ArrayList<Media> = arrayListOf()) {


        data class ProductTable (

            @SerializedName("id"             ) var id            : Int?    = null,
            @SerializedName("productName"    ) var productName   : String? = null,
            @SerializedName("description"    ) var description   : String? = null,
            @SerializedName("quantity"       ) var quantity      : Int?    = null,
            @SerializedName("maxPrice"       ) var maxPrice      : String? = null,
            @SerializedName("sellingPrice"   ) var sellingPrice  : String? = null,
            @SerializedName("category_id"    ) var categoryId    : Int?    = null,
            @SerializedName("subcategory_id" ) var subcategoryId : Int?    = null,
            @SerializedName("sku"            ) var sku           : String? = null

        )

        data class Category (

            @SerializedName("category_id" ) var categoryId  : Int?    = null,
            @SerializedName("name"        ) var name        : String? = null,
            @SerializedName("description" ) var description : String? = null,
            @SerializedName("image"       ) var image       : String? = null

        )
        data class Subcategory (

            @SerializedName("subCategoryId" ) var subCategoryId : Int?    = null,
            @SerializedName("name"          ) var name          : String? = null,
            @SerializedName("description"   ) var description   : String? = null,
            @SerializedName("image"         ) var image         : String? = null

        )
        data class Media (

            @SerializedName("id"         ) var id        : Int?    = null,
            @SerializedName("link"       ) var link      : String? = null,
            @SerializedName("product_id" ) var productId : Int?    = null

        )
}