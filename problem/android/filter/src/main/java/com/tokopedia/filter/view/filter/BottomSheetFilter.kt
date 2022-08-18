package com.tokopedia.filter.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.Slider
import com.tokopedia.filter.R
import com.tokopedia.filter.view.adapter.LocationFilterAdapter

class BottomSheetFilter : BottomSheetDialogFragment() {

    private lateinit var btnSubmit: AppCompatButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationFilterAdapter: LocationFilterAdapter
    private lateinit var filterListener : FilterListener
    private lateinit var sliderMin : Slider
    private lateinit var sliderMax : Slider
    private var minPriceSetup = 0
    private var maxPriceSetup = 0
    private var minPrice = 0
    private var maxPrice = 0
    private var locationFilter = ""

    private var listLocationFilter : ArrayList<String> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_location)
        sliderMin = view.findViewById(R.id.slider_min)
        sliderMax = view.findViewById(R.id.slider_max)
        btnSubmit = view.findViewById(R.id.btn_submit_filter)

        sliderMin.value = minPrice.toFloat()
        sliderMin.valueFrom = minPriceSetup.toFloat()
        sliderMin.valueTo = maxPriceSetup.toFloat()

        sliderMax.value = maxPrice.toFloat()
        sliderMax.valueFrom = minPriceSetup.toFloat()
        sliderMax.valueTo = maxPriceSetup.toFloat()

        locationFilterAdapter = LocationFilterAdapter()
        locationFilterAdapter.locationFilterActive = locationFilter
        locationFilterAdapter.updateData(listLocationFilter)
        recyclerView.layoutManager = LinearLayoutManager(view.rootView.context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = locationFilterAdapter

        btnSubmit.setOnClickListener {
            val minPriceFilter = sliderMin.value.toInt()
            val maxPriceFilter = sliderMax.value.toInt()
            minPrice = minPriceFilter
            maxPrice = maxPriceFilter
            locationFilter = locationFilterAdapter.locationFilterActive

            filterListener.onSubmitFilter(locationFilter, minPrice, maxPrice)
            dismiss()
        }
    }

    fun setListener(listener: FilterListener){
        this.filterListener = listener
    }

    fun filterData(location: String, minPrice: Int, maxPrice: Int){
        locationFilter = location
        this.minPrice = minPrice
        this.maxPrice = maxPrice
    }

    fun updateFilterSetup(locationList: ArrayList<String>, minPriceSetup: Int, maxPriceSetup: Int){
        listLocationFilter = locationList
        this.minPriceSetup = minPriceSetup
        this.maxPriceSetup = maxPriceSetup
        this.minPrice = minPriceSetup
        this.maxPrice = maxPriceSetup
    }

}