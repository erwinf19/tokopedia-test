package com.tokopedia.maps.model

import com.google.gson.annotations.SerializedName

class Idd {
    @SerializedName("root")
    var root : String? = null
    @SerializedName("suffixes")
    var suffix : List<String> = listOf()
}