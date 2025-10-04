package com.example.taxisharing.utils

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapPlotter {

    suspend fun plotIcon(map: GoogleMap, location: LatLng, context: Context) {
        try {
            if(map != null) {
                map.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title("Data")
                )
            }else {
                Log.i("TAXI-SHARING-INFORMATION", "Map is empty or null")
            }
        }catch (e: Exception) {
            Log.i("TAXI-SHARING-INFORMATION", e.message!!)
        }

    }

}