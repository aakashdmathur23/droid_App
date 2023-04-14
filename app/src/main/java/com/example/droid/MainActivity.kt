package com.example.droid

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionProvider
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    data class PackageInfo(
        var latLng: Boolean
    )

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mapFragment: SupportMapFragment ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<Button>(R.id.btn_get_tasks).setOnClickListener {
            fetchLocation()
        }
        setupMap()
    }

    private fun setupMap(){
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    val latLon1 = LatLng(51.3972,0.5386)
    val latLon2 = LatLng(51.3969,0.5522)
    val latLon3 = LatLng(51.3953,0.5496)
    val latLon4 = LatLng(51.3984,0.5395)

    override fun onMapReady(googleMap: GoogleMap) {

        val markerView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.marker_layout, null)
        val text = markerView.findViewById<TextView>(R.id.markerText)
        val cardView = markerView.findViewById<CardView>(R.id.markerCardView)

        text.text = "ACE IN ACE AT NELSON"
        val bitmap1 = Bitmap.createScaledBitmap(viewToBitmap(cardView)!!,cardView.width, cardView.height, false)
        val smallMarkerIcon1 = BitmapDescriptorFactory.fromBitmap(bitmap1)
        googleMap.addMarker(MarkerOptions().position(latLon1).icon(smallMarkerIcon1))

        text.text = "Buy Groceries from Asda"
        val bitmap2 = Bitmap.createScaledBitmap(viewToBitmap(cardView)!!,cardView.width, cardView.height, false)
        val smallMarkerIcon2 = BitmapDescriptorFactory.fromBitmap(bitmap2)
        googleMap.addMarker(MarkerOptions().position(latLon2).icon(smallMarkerIcon2))

        text.text = "Charging EV Car at Lidl"
        val bitmap3 = Bitmap.createScaledBitmap(viewToBitmap(cardView)!!,cardView.width, cardView.height, false)
        val smallMarkerIcon3 = BitmapDescriptorFactory.fromBitmap(bitmap3)
        googleMap.addMarker(MarkerOptions().position(latLon3).icon(smallMarkerIcon3))

        text.text = "Returning book to Library"
        val bitmap4 = Bitmap.createScaledBitmap(viewToBitmap(cardView)!!,cardView.width, cardView.height, false)
        val smallMarkerIcon4 = BitmapDescriptorFactory.fromBitmap(bitmap4)
        googleMap.addMarker(MarkerOptions().position(latLon4).icon(smallMarkerIcon4))
    }

    private fun viewToBitmap(view: View): Bitmap?{
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(0,0,view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

    private fun fetchLocation() {

        val task = fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
        }

        val list = ArrayList<LatLng>()
        list.add(latLon1)
        list.add(latLon2)
        list.add(latLon3)
        list.add(latLon4)

        task.addOnSuccessListener {
            if(it == list.contains(latLon1) || list.contains(latLon2) || list.contains(latLon3) || list.contains(latLon4))
                Toast.makeText(applicationContext, "You have task pending in the location", Toast.LENGTH_SHORT).show()
        }
    }
}