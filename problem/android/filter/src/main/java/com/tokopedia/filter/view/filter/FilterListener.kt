package com.tokopedia.filter.view.filter

interface FilterListener {

    fun onSubmitFilter(location : String = "", minPrice : Int = 0, maxPrice : Int = 0)

}