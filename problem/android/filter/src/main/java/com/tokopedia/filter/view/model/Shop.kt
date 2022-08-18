package com.tokopedia.filter.view.model

import com.google.gson.annotations.SerializedName

class Shop {
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("name")
    var name : String = ""
    @SerializedName("city")
    var city : String = ""
}