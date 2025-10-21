package com.example.taxisharing.model.vehicle

import com.example.taxisharing.model.driver.DriverModel
import com.example.taxisharing.model.location.LocationModel

// CarModel class is used to model the car response
data class VehicleModel(
    val category: String,
    val transmission: String,
    val level: String,
    val make: String,
    val model: String,
    val number_plate: String,
    val status: String,
    val cost_per_km: String,
    val location: LocationModel,
    val driver: DriverModel
)