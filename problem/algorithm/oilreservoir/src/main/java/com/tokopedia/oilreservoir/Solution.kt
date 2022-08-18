package com.tokopedia.oilreservoir

/**
 * Created by fwidjaja on 2019-09-24.
 */
object Solution {
    fun collectOil(height: IntArray): Int {
        val arraySize = height.size
        var totalOilCollect = 0
        var tempOilPredictCanCollect = 0
        var fixOilCanCollect = 0
        var highestBlockPosition = 0
        for(i in 0 until arraySize){
            if(i + 1 < arraySize){
                if(height[highestBlockPosition] > height[i+1]){
                    tempOilPredictCanCollect += (height[highestBlockPosition] - height[i + 1])
                    if(height[i] < height[i+1]){
                        var emptyRoom = 0
                        val differentHeight = height[highestBlockPosition] - height[i+1]
                        val distance = ((i+1) - highestBlockPosition)
                        for(j in 1..distance){
                            emptyRoom += if(height[highestBlockPosition + j] > height[i+1]){
                                differentHeight - (height[highestBlockPosition + j] - height[i+1])
                            }else{
                                differentHeight
                            }
                        }
                        fixOilCanCollect = tempOilPredictCanCollect - emptyRoom
                    }
                    if(i + 1 == arraySize - 1) totalOilCollect += fixOilCanCollect
                }else{
                    highestBlockPosition = i+1
                    totalOilCollect += tempOilPredictCanCollect
                    tempOilPredictCanCollect = 0
                    fixOilCanCollect = 0
                }
            }
        }
        return totalOilCollect
    }
}
