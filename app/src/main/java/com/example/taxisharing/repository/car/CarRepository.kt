package com.example.taxisharing.repository.car

import android.content.Context
import com.example.taxisharing.model.vehicle.VehicleResponseModel
import com.example.taxisharing.utils.FileUtil
import com.google.gson.Gson

// CardRepository is used to get the card information
class CarRepository {

    suspend fun getCardDataFromFile(fileName: String, context: Context): VehicleResponseModel {

        try {

        val res = FileUtil().readTextFromAsset(fileName, context)
        val resObj = Gson().fromJson(res, VehicleResponseModel::class.java)
        return resObj

        }catch (e: Exception) {
            return VehicleResponseModel(data = null)
        }
    }

}