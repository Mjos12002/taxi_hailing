package com.example.taxisharing.utils

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng


// Geocoding util is used to get the location name
class GeoCodingUtil {

    // getLocationName is the function used to load the location name information by supplying the latitude data
    suspend fun getLocationName(location: LatLng, context: Context): String{
        val geocoder = Geocoder(context)
        val currentLocationName = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        return currentLocationName?.get(0)?.getAddressLine(0) ?: "Votre position actuelle est inconnue."
    }

}