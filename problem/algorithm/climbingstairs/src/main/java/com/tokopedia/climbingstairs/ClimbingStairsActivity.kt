package com.tokopedia.climbingstairs

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.tokopedia.core.loadFile

class ClimbingStairsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem)
        val webView = findViewById<WebView>(R.id.webView)
        webView.loadFile("climbing_stairs.html")

        // example of how to call the function
        println("climb 5 ->" + Solution.climbStairs(5))
        println("climb 10 ->" + Solution.climbStairs(10))
        println("climb 20 ->" + Solution.climbStairs(20))
    }
}