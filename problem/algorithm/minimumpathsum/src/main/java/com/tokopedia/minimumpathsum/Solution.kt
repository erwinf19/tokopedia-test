package com.tokopedia.minimumpathsum

object Solution {
    fun minimumPathSum(matrix: Array<IntArray>): Int {
        val rows = matrix.size
        val cols = matrix[0].size

        for(i in 0 until rows){
            for(j in 0 until cols){
                if(i == 0 && j == 0){
                    continue
                }else if(i == 0){
                    matrix[i][j] += matrix[i][j - 1]
                }else if(j == 0){
                    matrix[i][j] += matrix[i - 1][j]
                }else{
                    matrix[i][j] += matrix[i][j - 1].coerceAtMost(matrix[i - 1][j])
                }
            }
        }
        return matrix[rows - 1][cols - 1]
    }
}
