package com.tokopedia.filter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tokopedia.filter.R
import com.tokopedia.filter.view.adapter.ProductAdapter
import com.tokopedia.filter.view.filter.BottomSheetFilter
import com.tokopedia.filter.view.filter.FilterListener
import com.tokopedia.filter.view.model.Product
import com.tokopedia.filter.view.model.Shop
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import kotlin.math.min

class ProductActivity : AppCompatActivity(), FilterListener {

    private var listDataProduct : ArrayList<Product> = arrayListOf()
    private var listLocation : ArrayList<String> = arrayListOf()
    private var listPrice : ArrayList<Int> = arrayListOf()
    private var locationFilterActive = ""
    private var minPriceFilter = 0
    private var maxPriceFilter = 0
    private lateinit var productAdapter : ProductAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var buttonFilter : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        getDataFromJsonFile()
        productAdapter = ProductAdapter()
        productAdapter.updateData(listDataProduct)
        recyclerView = findViewById(R.id.product_list)
        buttonFilter = findViewById(R.id.fab_filter)
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.adapter = productAdapter

        val locationCount = listLocation.groupingBy { it }.eachCount()
        val minPriceList = listPrice.min() ?: 0
        val maxPriceList = listPrice.max() ?: 0
        minPriceFilter = minPriceList
        maxPriceFilter = maxPriceList
        val sortedData = locationCount.toList()
            .sortedByDescending { (_, value) -> value }
            .toMap()

        val listLocationFilter : ArrayList<String> = arrayListOf()
        sortedData.forEach {
            listLocationFilter.add(it.key)
        }

        val bottomSheetFilter = BottomSheetFilter()
        bottomSheetFilter.updateFilterSetup(listLocationFilter, minPriceList, maxPriceList)
        bottomSheetFilter.setListener(this)

        buttonFilter.setOnClickListener {
            bottomSheetFilter.filterData(locationFilterActive, minPriceFilter, maxPriceFilter)
            bottomSheetFilter.show(supportFragmentManager, "BottomSheetDialog")
        }
    }

    private fun readJSONData() : String? {
        val inputStream : InputStream?
        val json : String

        try {
            inputStream = resources.openRawResource(R.raw.products)
            val size = inputStream.available()
            val buffer  = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            json = String(buffer, Charsets.UTF_8)
        }catch (e : IOException) {
            e.printStackTrace()
            return null
        }
        return json
    }

    private fun getDataFromJsonFile(){
        val jsonObject = readJSONData()?.let { JSONObject(it) }
        val data = jsonObject?.getJSONObject("data")
        val product = data?.getJSONArray("products")
        val productSize = product?.length() ?: 0

        for(i in 0 until productSize){
            val productDetail = product?.getJSONObject(i)
            val newProduct = Product()
            val newShop = Shop()
            newProduct.id = productDetail?.getInt("id") ?: 0
            newProduct.name = productDetail?.getString("name") ?: ""
            newProduct.imageUrl = productDetail?.getString("imageUrl") ?: ""
            newProduct.priceInt = productDetail?.getInt("priceInt") ?: 0
            newProduct.discountPercentage = productDetail?.getInt("discountPercentage") ?: 0
            newProduct.slashedPriceInt = productDetail?.getInt("slashedPriceInt") ?: 0

            val shopDetail = productDetail?.getJSONObject("shop")

            newShop.id = shopDetail?.getInt("id") ?: 0
            newShop.name = shopDetail?.getString("name") ?: ""
            newShop.city = shopDetail?.getString("city") ?: ""

            newProduct.shop = newShop
            listLocation.add(newShop.city)
            listPrice.add(newProduct.priceInt)
            listDataProduct.add(newProduct)
        }
    }

    override fun onSubmitFilter(location: String, minPrice: Int, maxPrice: Int) {
        locationFilterActive = location
        minPriceFilter = minPrice
        maxPriceFilter = maxPrice

        val filterData = arrayListOf<Product>()

        listDataProduct.forEach { product ->
            val maxPriceRange: Boolean = if(minPriceFilter > 0) product.priceInt < maxPriceFilter else true
            val rangePrice = (minPriceFilter < product.priceInt) && maxPriceRange
            val rangeLocation = if(locationFilterActive != "") product.shop.city == locationFilterActive else true
            if(rangeLocation && rangePrice){
                filterData.add(product)
            }
        }
        productAdapter.updateData(filterData)
    }
}