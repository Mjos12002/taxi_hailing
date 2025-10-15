package com.example.taxisharing

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.taxisharing.databinding.ActivityMainBinding
import com.example.taxisharing.utils.GeoCodingUtil
import com.example.taxisharing.viewmodel.car.CarViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxisharing.adapter.CarListAdapter
import com.example.taxisharing.model.car.CarModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val pERMISSION_ID = 42
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var mMap: GoogleMap
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var currentLocation: LatLng = LatLng(20.5, 78.9)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // View model initialization
        val carViewModel = ViewModelProvider(this)[CarViewModel::class.java]

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Click event to book a taxi
        binding.root.findViewById<Button>(R.id.btn_book_taxi).setOnClickListener {
            val filename = "taxi.json"
            try {
                displayBottomDialog(this, carViewModel)
//                lifecycleScope.launch {
//                    carViewModel.getCardInformation(filename, applicationContext)
//                }
            }catch (e: Exception) {
                Log.i("TAXI-SHARING-INFORMATION", "hello error ${e}")
            }

        }

        // Listen for changes in card view model
        carViewModel.cardResponseLiveData.observe(this, Observer {
            try{
                Toast.makeText(applicationContext, "Before evaluation", Toast.LENGTH_LONG).show()
                if(it.data?.isNotEmpty() == true) {
                    //displayBottomDialog(this)
                    it.data.forEach { it ->
                        Toast.makeText(applicationContext, "For each", Toast.LENGTH_LONG).show()
                        lifecycleScope.launch {
                            //n.plotIcon(mMap, LatLng(it.location.latitude.toDouble(), it.location.longitude.toDouble()))
                            addCarIcons(mMap, LatLng(it.location.latitude.toDouble(), it.location.longitude.toDouble()), it)
                        }
                    }
                }else{
                    Toast.makeText(applicationContext, "Is null", Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception) {
                Log.i("TAXI-SHARING-INFORMATION", e.message!!)
            }
        })
    }

    suspend fun addCarIcons(map: GoogleMap, location: LatLng, data: CarModel) {

        try {
            val drawableId = resources.getIdentifier(data.category, "drawable", packageName)
            if(map != null) {
                map.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(data.make + " " + data.model)
                        .icon(bitmapFromVector(
                            applicationContext,
                            drawableId))
                )
            }else {
                Log.i("TAXI-SHARING-INFORMATION", "Map is empty or null")
            }
        }catch (e: Exception) {
            Log.i("TAXI-SHARING-INFORMATION", e.message!!)
        }

    }

    fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {

        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable?.setBounds(
            0, 0, vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = createBitmap(vectorDrawable?.intrinsicWidth!!, vectorDrawable.intrinsicHeight)

        val canvas = Canvas(bitmap)

        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLastLocation()
    }

    // Get current location
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        // Get the current location longitude and latitude
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(currentLocation))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
                        // Getting the location name
                        lifecycleScope.launch {
                            try{
                                val loc = GeoCodingUtil().getLocationName(currentLocation, baseContext)
                                binding.root.findViewById<TextView>(R.id.tv_current_address).text = loc
                            }catch (e: Exception) {
                                Toast.makeText(applicationContext, e.message!!, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    // Get current location, if shifted from previous location
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        mLocationRequest

        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    // If current location could not be located, use last location
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation!!
            currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    // function to check if GPS is on
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Check if location permissions are
    // granted to the application
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // Request permissions if not granted before
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            pERMISSION_ID
        )
    }

    // What must happen when permission is granted
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == pERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun displayBottomDialog(context: Context, carViewModel: CarViewModel) {
        try{
                lifecycleScope.launch {
                    val taxi = carViewModel.loadCardInformation("taxi.json", context)

                    val dialog = BottomSheetDialog(context)
                    val inflater = layoutInflater.inflate(R.layout.car_list_dialog, null)
                    val rv = inflater.findViewById<RecyclerView>(R.id.rv_car_availability)
                    val rlLayout = LinearLayoutManager(context)
                    rv.layoutManager = rlLayout
                    val carAvailabilityAdapter = CarListAdapter(taxi.data!!)
                    rv.adapter = carAvailabilityAdapter
                    dialog.setCancelable(true)
                    dialog.setContentView(inflater)
                    dialog.show()
                }

        }catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            Log.i("TAXI-SHARING-INFORMATION", e.message!!)
        }


    }

}