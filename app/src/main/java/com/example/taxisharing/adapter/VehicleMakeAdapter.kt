package com.example.taxisharing.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.taxisharing.R
import com.example.taxisharing.model.vehicle.VehicleMakeModel

class VehicleMakeAdapter(private val context: Context, private val carMakeList: List<VehicleMakeModel>): BaseAdapter() {
    override fun getCount(): Int {
        return carMakeList.size
    }

    override fun getItem(p0: Int): Any? {
        return carMakeList[p0]
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
        val view = inflater.inflate(R.layout.vehicle_make_items, p2, false)
        val item = getItem(p0) as VehicleMakeModel
        // Bind data to views
        val text = view.findViewById<TextView>(R.id.tv_make_name)
        text.text = item.name

        return view

    }
}