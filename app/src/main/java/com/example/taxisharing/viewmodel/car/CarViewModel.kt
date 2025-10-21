package com.example.taxisharing.viewmodel.car

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taxisharing.model.vehicle.VehicleResponseModel
import com.example.taxisharing.repository.car.CarRepository

// CardViewModel is used to manage card response
class CarViewModel: ViewModel() {

    private val _carResponseMutableLiveData = MutableLiveData<VehicleResponseModel>()
    val cardResponseLiveData: LiveData<VehicleResponseModel> = _carResponseMutableLiveData

    // getCardInformation is a method used to set the card information
    suspend fun getCardInformation(fileName: String, context: Context) {
        val res = CarRepository().getCardDataFromFile(fileName, context)
        _carResponseMutableLiveData.postValue(res)
    }

    suspend fun loadCardInformation(fileName: String, context: Context): VehicleResponseModel {
        return CarRepository().getCardDataFromFile(fileName, context)
    }

}