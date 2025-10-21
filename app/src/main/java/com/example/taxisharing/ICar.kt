package com.example.taxisharing

import com.example.taxisharing.model.vehicle.VehicleResponseModel

// ICar is an interface to abstract car related activities
interface ICar {

    suspend fun GetCar(): VehicleResponseModel

}