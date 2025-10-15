package com.example.taxisharing.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taxisharing.R
import com.example.taxisharing.model.car.CarModel

// CarListAdapter is used to display the list of cars near the customer
class CarListAdapter(private val carList: List<CarModel>): RecyclerView.Adapter<CarListAdapter.CarListAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarListAdapterViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.car_available_list, parent, false)
        return CarListAdapterViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: CarListAdapterViewHolder,
        position: Int
    ) {
        holder.apply {
            Log.i("TAXI-SHARING-INFORMATION", "${carList[position]}")
            tvCarMake.text = carList[position].make
            tvCarAvailability.text = carList[position].model
        }
    }

    override fun getItemCount(): Int {
        return carList.size
    }


    inner class CarListAdapterViewHolder(val vw: View): RecyclerView.ViewHolder(vw){
        val tvCarMake = vw.findViewById<TextView>(R.id.tv_car_make)
        val tvCarAvailability = vw.findViewById<TextView>(R.id.tv_car_availability)
    }

}