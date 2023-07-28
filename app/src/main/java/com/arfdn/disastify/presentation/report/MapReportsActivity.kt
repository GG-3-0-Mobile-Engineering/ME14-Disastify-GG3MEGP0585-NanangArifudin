package com.arfdn.disastify.presentation.report

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
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
import com.arfdn.disastify.presentation.customview.DateTimePickerFragment
import com.arfdn.disastify.presentation.customview.OnClickTimePicker
import com.arfdn.disastify.presentation.customview.TimePickerDialog
import com.arfdn.disastify.presentation.customview.WaitingDialog
import com.arfdn.disastify.presentation.helper.NotificationHelper
import com.arfdn.disastify.presentation.helper.WaterLevelCheckWorker
import com.arfdn.disastify.presentation.profile.ProfileActivity
import com.arfdn.disastify.utils.startActivity
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class MapReportsActivity : AppCompatActivity(), OnMapReadyCallback
, DateTimePickerFragment.DateTimeListener{

    private lateinit var mMap: GoogleMap
    private var startTime: String? = null
    private var endTime: String? = null
    private lateinit var binding: ActivityMapReportsBinding
    private val disasterListViewModel: DisasterListViewModel by viewModel()
    val chipData = arrayListOf<String>("all","flood", "haze", "fire", "wind", "volcano", "earthquake")
    private val dialog by lazy{ WaitingDialog(this)}

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shownotif()
        val waterLevelThreshold = 1 // Nilai ambang batas untuk menampilkan notifikasi (sesuaikan dengan kebutuhan Anda)
        NotificationHelper.checkWaterLevel(this, waterLevelThreshold)
        // Buat PeriodicWorkRequest untuk WaterLevelCheckWorker
        val workRequest = PeriodicWorkRequest.Builder(
            WaterLevelCheckWorker::class.java,
            15, // Interval waktu untuk menjalankan worker (dalam menit), contoh: setiap 15 menit
            TimeUnit.MINUTES
        ).build()

        // Jadwalkan dan jalankan worker secara berkala
        WorkManager.getInstance(applicationContext).enqueue(workRequest)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        with(binding){
            layoutSearchFilter.circleImageView.setOnClickListener {
                this@MapReportsActivity.startActivity<ProfileActivity>()
            }
        }
        dialog.show()
    }

    private fun shownotif() {
        NotificationHelper.createNotificationChannel(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        Log.d("DisasterData", "Log biasa")
        disasterListViewModel.getDisasterReports("432000",null,null)
//        val startDate = "2023-07-01T00:00:00Z"
//        val endDate = "2023-07-31T23:59:59Z"
//        disasterListViewModel.getDisasterReportsByPeriod(startDate, endDate)
        disasterListViewModel.disasterList.observe(this@MapReportsActivity, Observer { disasters ->
            dialog.dismiss()
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
                    when(typeSelected){
                        "input period" -> {
                            showDateTimePickerDialog()
                        }
                        "all" -> {
                            disasterListViewModel.getDisasterReports("432000", null, null)
                            dialog.show()
                        }
                        else -> {
                            disasterListViewModel.getDisasterReports(null, null, typeSelected)
                            dialog.show()
                        }
                    }
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
                dialog.show()
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
    private fun showDateTimePickerDialog() {
//        val pickerDialog = TimePickerDialog(this, supportFragmentManager, object :
//            OnClickTimePicker {
//            @RequiresApi(Build.VERSION_CODES.O)
//            override fun onClickTimePicker(startTime: String, endTime: String) {
//                Log.d("DateTimePicker", "Start Time: $startTime")
//                Log.d("DateTimePicker", "End Time: $endTime")
//                disasterListViewModel.getDisasterReportsByPeriod(convertDateFormat(startTime), convertDateFormat(endTime))
//                dialog.show()
//            }
//        })
//        pickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateFormat(inputDate: String): String {
        // Define the input format
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")

        // Parse the input date string to an OffsetDateTime object
        val offsetDateTime = OffsetDateTime.parse(inputDate, inputFormat)

        // Convert to UTC (Zona waktu 0)
        val utcOffsetDateTime = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC)

        // Define the output format
        val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

        // Format the UTC OffsetDateTime to the desired output format
        return outputFormat.format(utcOffsetDateTime)
    }

    override fun onDateTimeSet(dateTime: String, isStartTime: Boolean) {
        if (isStartTime) {
            startTime = dateTime
            Log.d("DateTimePicker", "Start Time: $dateTime")
            // Use the startTime as needed (e.g., display in TextView)
        } else {
            endTime = dateTime
            Log.d("DateTimePicker", "End Time: $dateTime")
            // Use the endTime as needed (e.g., display in TextView)
        }
    }
}