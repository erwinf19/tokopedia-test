package com.tokopedia.climbingstairs

object Solution {
    fun climbStairs(n: Int): Long {

        var totalWay = 0
        var a = 0
        var b = 1
        for(i in 1..n + 1){
            totalWay = a + b
            a = b
            b = totalWay
        }

        return totalWay.toLong()
    }
}
