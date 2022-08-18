package com.tokopedia.oilreservoir

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.tokopedia.core.loadFile

/**
 * Created by fwidjaja on 2019-09-24.
 */
class OilReservoirActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem)

        val webView = findViewById<WebView>(R.id.webView)
        webView.loadFile("oil_reservoir.html")

        // example of how to call the function
        println("Total Oil Collected" + Solution.collectOil(intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)))
        println("Total Oil Collected" + Solution.collectOil(intArrayOf(1, 4, 2, 3, 1, 1, 1, 2, 0, 1, 3, 1)))
        println("Total Oil Collected" + Solution.collectOil(intArrayOf(2, 2, 1, 0, 5, 0, 2, 0, 3, 0, 2, 4)))
    }

}