package com.example.taxisharing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taxisharing.databinding.FragmentFirstBinding
import com.example.taxisharing.ui.home.HomeViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.age.observe(viewLifecycleOwner) {
            val osVersion = android.os.Build.VERSION.SDK_INT_FULL
            val sdkVersion = android.os.Build.VERSION.SDK_INT
            val x = android.os.Build.VERSION.CODENAME
            Toast.makeText(requireContext(), "$it $osVersion $sdkVersion $x", Toast.LENGTH_LONG).show()
        }

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}