package com.example.taxisharing.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.taxisharing.R
import com.example.taxisharing.model.vehicle.VehicleCategoryModel
import com.example.taxisharing.model.vehicle.VehicleModelModel

class VehicleModelAdapter(private val context: Context, private val vehicleModelList: List<VehicleModelModel>): BaseAdapter() {
    override fun getCount(): Int {
        return vehicleModelList.size
    }

    override fun getItem(p0: Int): Any? {
        return vehicleModelList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(
        p0: Int,
        p1: View?,
        p2: ViewGroup?
    ): View? {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.vehicle_model_items, p2, false)

        val item = getItem(p0) as VehicleModelModel

        // Bind data to views
        val text = view.findViewById<TextView>(R.id.tv_model_name)

        text.text = item.name

        return view

    }
}