package com.tokopedia.maps.model

import com.google.gson.annotations.SerializedName

class CountryName {
    @SerializedName("common")
    var common : String? = null
    @SerializedName("official")
    var official : String? = null
}