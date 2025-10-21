package com.example.taxisharing.ui.vehicle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taxisharing.R
import com.example.taxisharing.adapter.VehicleSpinnerAdapter
import com.example.taxisharing.databinding.FragmentVehicleBinding
import com.example.taxisharing.model.vehicle.VehicleItemModel

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
    val vehicleCategory = listOf<VehicleItemModel>(
        VehicleItemModel(R.drawable.car, "Car"),
        VehicleItemModel(R.drawable.bus, "Bus"),
        VehicleItemModel(R.drawable.jeep, "SUV")
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
        val vehicleAdapter = VehicleSpinnerAdapter(requireContext(), vehicleCategory)
        // Initialize the spinner
        val spnVehicle = binding.spVehicleCategory
        spnVehicle.adapter = vehicleAdapter

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