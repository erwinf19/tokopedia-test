package com.tokopedia.filter.view.model

import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("name")
    var name : String = ""
    @SerializedName("imageUrl")
    var imageUrl : String = ""
    @SerializedName("priceInt")
    var priceInt : Int = 0
    @SerializedName("discountPercentage")
    var discountPercentage : Int = 0
    @SerializedName("slashedPriceInt")
    var slashedPriceInt : Int = 0
    @SerializedName("shop")
    var shop : Shop = Shop()
}