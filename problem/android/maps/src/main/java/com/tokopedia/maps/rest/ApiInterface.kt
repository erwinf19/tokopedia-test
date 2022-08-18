package com.tokopedia.maps.rest

import com.tokopedia.maps.model.CountryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("name/{country}")
    fun getSearchCountryData(
        @Path("country") country: String
    ) : Call<List<CountryResponse>>
}