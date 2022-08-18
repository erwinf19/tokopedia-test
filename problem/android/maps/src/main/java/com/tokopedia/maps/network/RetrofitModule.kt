package com.tokopedia.maps.network

import com.google.gson.GsonBuilder
import com.tokopedia.maps.rest.ApiInterface
import com.tokopedia.maps.rest.MapsConstant
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitModule {

    private lateinit var apiInterface: ApiInterface
    private lateinit var httpClient: OkHttpClient
    private var retrofit: Retrofit? = null
    var url : String = ""


    fun getDefaultInterface () : ApiInterface {

        val gson = GsonBuilder().setLenient().create()
        url = MapsConstant.BASE_URL
        httpClient = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        httpClient.connectTimeoutMillis()
        httpClient.readTimeoutMillis()

        if (retrofit == null) {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .baseUrl(url)
            retrofit = retrofitBuilder.build()
        }
        apiInterface = retrofit!!.create(ApiInterface::class.java)
        return apiInterface
    }
}