package com.arfdn.disastify.presentation.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.arfdn.disastify.R
import com.arfdn.disastify.data.model.provinces

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.arfdn.disastify.databinding.ActivityMapReportsBinding
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapReportsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapReportsBinding
    private val disasterListViewModel: DisasterListViewModel by viewModel()
    val chipData = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        Log.d("DisasterData", "Log biasa")

        disasterListViewModel.getDisasterReports()
        disasterListViewModel.disasterList.observe(this@MapReportsActivity, Observer { disasters ->
            // Data retrieval is successful, log the data here
            Log.d(
                "DisasterData",
                "Disaster Data Retrieved: ${disasters.result?.type ?: "nodata"}"
            )
            val gson = Gson()
            val dataJson = gson.toJson(disasters)
            Log.d("cekmapp", "onMapReady:  ${dataJson}")

            disasters.result?.objects?.output?.geometries?.forEach { geo ->

                if (!(chipData.contains(geo.properties.disasterType))){
                    chipData.add(geo.properties.disasterType)
                Log.d("cekmapp", "onMapReady: ${chipData.size}")
                }
                val sydney =
                    LatLng(geo.coordinates[1], geo.coordinates[0])
                mMap.addMarker(
                    MarkerOptions().position(sydney)
                        .title((geo.properties.title ?: "-").toString())
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

            }
            for (chipText in chipData) {
                val chip = Chip(this@MapReportsActivity)
                chip.text = chipText
                chip.isAllCaps = true
//                chip.setChipIconResource(R.drawable.ic_plus)
                chip.isCheckable = true
                chip.isCloseIconVisible = false
                binding.layoutSearchFilter.chipGroup.addView(chip)
            }


        })

        val provinceNames = provinces.values.toList()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, provinceNames)
        binding.layoutSearchFilter.edtSearch.setAdapter(adapter)
        
    }
}