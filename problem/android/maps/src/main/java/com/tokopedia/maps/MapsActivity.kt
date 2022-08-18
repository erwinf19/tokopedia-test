package com.tokopedia.maps

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tokopedia.core.MsgDialog
import com.tokopedia.core.ProgressDialog
import com.tokopedia.maps.model.CountryResponse
import com.tokopedia.maps.network.RetrofitModule
import com.tokopedia.maps.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MapsActivity : AppCompatActivity() {

    val loading = ProgressDialog(this@MapsActivity)
    val msgDialog = MsgDialog(this@MapsActivity)
    private var apiService : ApiInterface = RetrofitModule().getDefaultInterface()
    private var mapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null
    private lateinit var textCountryName: TextView
    private lateinit var textCountryCapital: TextView
    private lateinit var textCountryPopulation: TextView
    private lateinit var textCountryCallCode: TextView

    private var editText: EditText? = null
    private var buttonSubmit: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        bindViews()
        initListeners()
        loadMap()
    }

    private fun bindViews() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        editText = findViewById(R.id.editText)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        textCountryName = findViewById(R.id.txtCountryName)
        textCountryCapital = findViewById(R.id.txtCountryCapital)
        textCountryPopulation = findViewById(R.id.txtCountryPopulation)
        textCountryCallCode = findViewById(R.id.txtCountryCallCode)
    }

    private fun initListeners() {
        buttonSubmit!!.setOnClickListener {
            val inputText = editText?.text.toString()
            getCountry(inputText, ::onSuccess)
        }
    }

    private fun loadMap(){
        mapFragment?.getMapAsync { googleMap -> this@MapsActivity.googleMap = googleMap }
    }

    private fun getCountry(countryName: String, onSuccess: (List<CountryResponse>) -> Unit){
        loading.startLoading()
        val call = apiService.getSearchCountryData(countryName)
        call.enqueue(object : Callback<List<CountryResponse>> {
            override fun onResponse(call: Call<List<CountryResponse>>, response: Response<List<CountryResponse>>) {

                loading.isDismiss()
                val body = response.body()
                if(body!=null){
                    onSuccess(body)
                }else{
                    msgDialog.isShow("The country you are looking for does not exist")
                }
            }

            override fun onFailure(call: Call<List<CountryResponse>>, t: Throwable) {
                loading.isDismiss()
                msgDialog.isShow("Failed, network error")
            }
        })
    }

    private fun onSuccess(listCountryResponse: List<CountryResponse>){
        googleMap?.clear()
        val totalData = listCountryResponse.size
        val lastData = listCountryResponse[totalData-1]
        val countryName = getString(R.string.countryName) + " " + lastData.name.common
        val capital = if(lastData.capital.isNotEmpty()) getString(R.string.capital) + " " + lastData.capital[0] else getString(R.string.capital) + " " + "0"
        val population = getString(R.string.population) + " " + convertDecimalFormat(lastData.population)
        val suffix = if(lastData.idd.suffix.isNotEmpty()) lastData.idd.suffix[0] else ""
        val callCode = lastData.idd.root + suffix
        val callCodeText = getString(R.string.callCode) + " " + callCode

        textCountryName.text = countryName
        textCountryCapital.text = capital
        textCountryPopulation.text = population
        textCountryCallCode.text = callCodeText
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lastData.latLng[0], lastData.latLng[1])))

        listCountryResponse.forEach { country ->
            val latitude = country.latLng[0]
            val longitude = country.latLng[1]
            val latLng = LatLng(latitude, longitude)
            googleMap?.addMarker(MarkerOptions().position(latLng).title(country.name.common))
        }
    }

    private fun convertDecimalFormat(population: String): String {
        val locale = Locale("in", "id")
        val symbols = DecimalFormatSymbols.getInstance(locale)
        symbols.groupingSeparator = '.'
        symbols.monetaryDecimalSeparator = ','
        symbols.currencySymbol = ""
        val df = DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
        val numberFormat = DecimalFormat(df.toPattern(), symbols)
        numberFormat.maximumFractionDigits = 0

        return if (TextUtils.isEmpty(population)) {
            numberFormat.format(0)
        } else {
            numberFormat.format(java.lang.Double.parseDouble(population))
        }
    }
}
