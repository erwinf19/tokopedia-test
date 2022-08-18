package com.tokopedia.maps.model

import com.google.gson.annotations.SerializedName

class CountryResponse {
    @SerializedName("name")
    var name : CountryName = CountryName()
    @SerializedName("capital")
    var capital : List<String> = listOf()
    @SerializedName("population")
    var population : String = "0"
    @SerializedName("idd")
    var idd : Idd = Idd()
    @SerializedName("latlng")
    var latLng : List<Double> = listOf(0.0, 0.0)
}

