package com.tokopedia.filter.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.filter.R

class LocationFilterAdapter : RecyclerView.Adapter<LocationFilterAdapter.LocationChipsViewHolder>() {

    private var locationList = ArrayList<String>()
    var locationFilterActive = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationChipsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LocationChipsViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: LocationChipsViewHolder, position: Int) {
            holder.bind(locationList[position])
    }

    override fun getItemCount(): Int {
        return if(locationList.size > 3)
            3
        else
            locationList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: ArrayList<String>){
        locationList = newData
        notifyDataSetChanged()
    }

    inner class LocationChipsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_chips, parent, false)) {

        private var tvLabel : TextView? = null
        private var cardItem : RelativeLayout? = null

        init {
            tvLabel = itemView.findViewById(R.id.tv_label)
            cardItem = itemView.findViewById(R.id.card_item)
        }

        fun bind(location : String){
            val context = itemView.context
            tvLabel?.text = location
            if(location == locationFilterActive){
                cardItem?.background = ContextCompat.getDrawable(context, R.drawable.bg_flexbox_active)
            }else{
                cardItem?.background = ContextCompat.getDrawable(context, R.drawable.bg_flexbox_inactive)
            }

            itemView.setOnClickListener {
                locationFilterActive = if (location != locationFilterActive) location else ""
                notifyDataSetChanged()
            }
        }
    }
}