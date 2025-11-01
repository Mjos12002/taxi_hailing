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

class VehicleCategoryAdapter(private val context: Context, private val items: List<VehicleCategoryModel>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    // Create and return the view for each item in the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.vehicle_category_items, parent, false)

        val item = getItem(position) as VehicleCategoryModel

        // Bind data to views
        val icon = view.findViewById<ImageView>(R.id.iv_vehicle_icon)
        val text = view.findViewById<TextView>(R.id.tv_vehicle_text)

        icon.setImageResource(item.iconResId)
        text.text = item.itemName

        return view
    }

}