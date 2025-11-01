package com.example.taxisharing.ui.vehicle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taxisharing.R
import com.example.taxisharing.adapter.VehicleCategoryAdapter
import com.example.taxisharing.adapter.VehicleMakeAdapter
import com.example.taxisharing.adapter.VehicleModelAdapter
import com.example.taxisharing.databinding.FragmentVehicleBinding
import com.example.taxisharing.model.vehicle.VehicleCategoryModel
import com.example.taxisharing.model.vehicle.VehicleMakeModel
import com.example.taxisharing.model.vehicle.VehicleModelModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VehicleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VehicleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Initialize the binding of fragment to the UI
    private var _binding: FragmentVehicleBinding? = null

    // Get and assign the binding to the new variable
    private val binding get() = _binding!!

    // List of category of vehicle
    val vehicleCategory = listOf<VehicleCategoryModel>(
        VehicleCategoryModel(R.drawable.car, "Car"),
        VehicleCategoryModel(R.drawable.bus, "Bus"),
        VehicleCategoryModel(R.drawable.jeep, "SUV")
    )

    val vehicleMake = listOf<VehicleMakeModel>(
        VehicleMakeModel(1, "Mercedes Benz"),
        VehicleMakeModel(2, "Toyota"),
        VehicleMakeModel(3, "Hyundai"),
        VehicleMakeModel(4, "BMW")
    )

    val vehicleModel = listOf<VehicleModelModel>(
        VehicleModelModel(1, "S400", 1),
        VehicleModelModel(2, "Land Cruiser", 2),
        VehicleModelModel(3, "3 Series", 4)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVehicleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create a variable to hold the vehicle spinner adapter
        val vehicleCategoryAdapter = VehicleCategoryAdapter(requireContext(), vehicleCategory)
        val vehicleMakeAdapter = VehicleMakeAdapter(requireContext(), vehicleMake)
        val vehicleModelAdapter = VehicleModelAdapter(requireContext(), vehicleModel)

        // Initialize the spinner
        val spnVehicleCategory = binding.spVehicleCategory
        val spnVehicleModel = binding.spVehicleModel
        val spnVehicleMake = binding.spVehicleMake

        spnVehicleCategory.adapter = vehicleCategoryAdapter
        spnVehicleModel.adapter = vehicleModelAdapter
        spnVehicleMake.adapter = vehicleMakeAdapter

        Log.i("TAXI-INFORMATION", "Using this feature for now")
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VehicleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}