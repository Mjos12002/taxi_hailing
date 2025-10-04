package com.example.taxisharing

import com.example.taxisharing.model.car.CarResponseModel

// ICar is an interface to abstract car related activities
interface ICar {

    suspend fun GetCar(): CarResponseModel

}