package com.arfdn.disastify.presentation.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.arfdn.disastify.R
import com.arfdn.disastify.data.model.Geometry
import com.arfdn.disastify.data.model.provinces

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.arfdn.disastify.databinding.ActivityMapReportsBinding
import com.arfdn.disastify.presentation.profile.ProfileActivity
import com.arfdn.disastify.utils.startActivity
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapReportsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapReportsBinding
    private val disasterListViewModel: DisasterListViewModel by viewModel()
    val chipData = arrayListOf<String>("all","flood", "haze", "fire", "wind", "volcano", "earthquake")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        with(binding){
            layoutSearchFilter.circleImageView.setOnClickListener {
                this@MapReportsActivity.startActivity<ProfileActivity>()
            }

        }

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

        disasterListViewModel.getDisasterReports(null,null,null)
        disasterListViewModel.disasterList.observe(this@MapReportsActivity, Observer { disasters ->
            // Data retrieval is successful, log the data here
            Log.d(
                "DisasterData",
                "Disaster Data Retrieved: ${disasters.result?.type ?: "nodata"}"
            )
            val gson = Gson()
            val dataJson = gson.toJson(disasters)
            Log.d("cekmapp", "onMapReady:  ${dataJson}")

            disasters.result?.objects?.output?.geometries?.let { geo ->
                showBottomSheet(geo)
                binding.fab.setOnClickListener {
                    showBottomSheet(geo)
                }
            }


            disasters.result?.objects?.output?.geometries?.forEach { geo ->
                val sydney =
                    LatLng(geo.coordinates[1], geo.coordinates[0])
                mMap.addMarker(
                    MarkerOptions().position(sydney)
                        .title((geo.properties.text ?: "-").toString())
                )
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))

            }
            for (chipText in chipData) {
                val chip = Chip(this@MapReportsActivity)
                chip.text = chipText
                chip.isAllCaps = true
                chip.isCheckable = true
                chip.isCloseIconVisible = false
                binding.layoutSearchFilter.chipGroup.addView(chip)
                chip.setOnClickListener {
                    val selectedChipText = chip.text.toString()
                    val typeSelected = selectedChipText.lowercase()
                    if (typeSelected=="all"){
                        disasterListViewModel.getDisasterReports(null, null, null)
                    } else disasterListViewModel.getDisasterReports(null, null, typeSelected)
                    Log.d("SelectedChip", "Selected: $selectedChipText")
                }
            }
        })

        val provinceNames = provinces.values.toList()
        val reversedProvinces = provinces.entries.associate { it.value to it.key }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, provinceNames)
        binding.layoutSearchFilter.edtSearch.setAdapter(adapter)
        binding.layoutSearchFilter.edtSearch.setOnItemClickListener { adapterView, view, position, id ->
            val selectedProvince = adapterView.getItemAtPosition(position).toString()
            val provinceId = reversedProvinces[selectedProvince]

            if (provinceId != null) {
                // The province ID was found in the map
                disasterListViewModel.getDisasterReports(null, provinceId.toString(),null )
                println("Province: $selectedProvince, Province ID: $provinceId")
            } else {
                println("Province: $selectedProvince not found.")
            }
        }

    }

    private fun showBottomSheet(geo: List<Geometry>) {
        val bottomSheetFragment = ListDisasterBottomSheetDialogFragment(geo)
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}