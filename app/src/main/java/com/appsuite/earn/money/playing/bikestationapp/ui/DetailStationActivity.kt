package com.appsuite.earn.money.playing.bikestationapp.ui

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.appsuite.earn.money.playing.bikestationapp.Constants
import com.appsuite.earn.money.playing.bikestationapp.R
import com.appsuite.earn.money.playing.bikestationapp.databinding.ActivityDetailStationBinding
import com.appsuite.earn.money.playing.bikestationapp.model.Feature
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.roundToInt


class DetailStationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDetailStationBinding
    private lateinit var data: Feature
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getParcelableExtra<Feature>("data")!!
        data?.let {
            binding.boomShhet.availablePlaces.setText(it.properties.free_racks)
            binding.boomShhet.tvLabelParking.setText(it.properties.label)
            binding.boomShhet.tvAvailableBikes.setText(it.properties.bikes)

            if (Constants.location != null) {
                var myLocation = Constants.location
                var distance = Constants.distance(
                    myLocation!!.latitude,
                    myLocation.longitude,
                    data.geometry.coordinates.get(0),
                    data.geometry.coordinates.get(0)
                )
                distance = distance * 1.6;
                distance = (distance * 100.0).roundToInt() / 100.0
                binding.boomShhet.tvDistance.setText("$distance km-station")
            }
        }


        val mapFragment = supportFragmentManager
            .findFragmentById(com.appsuite.earn.money.playing.bikestationapp.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun createDrawableFromView(context: Context, view: View): Bitmap? {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay
            .getMetrics(displayMetrics)
        view.layoutParams = ActionBar.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(
            0, 0, displayMetrics.widthPixels,
            displayMetrics.heightPixels
        )
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val markerView: View = (this
            .getSystemService(
                LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater)
            .inflate(R.layout.custom_marker, null)
        markerView.findViewById<TextView>(R.id.tv_free_space)
            .setText(data.properties.bikes)
        var sydney:LatLng
        if(Constants.location!=null)
        {
             sydney = LatLng(Constants.location!!.latitude, Constants.location!!.longitude)
        }else
        {
            sydney = LatLng(data.geometry.coordinates.get(0), data.geometry.coordinates.get(1))
        }
        mMap.addMarker(MarkerOptions().position(sydney).title(data.properties.label).icon(bitmapDescriptorFromVector(this, R.drawable.ic_gps))
            )
        mMap.addMarker(
            MarkerOptions()
                .title(data.properties.label)
                .position(
                    LatLng(
                        data.geometry.coordinates.get(0), data.geometry.coordinates.get(1)
                    )
                )
                .icon(
                    BitmapDescriptorFactory
                        .fromBitmap(
                            createDrawableFromView(
                                this,
                                markerView
                            )!!
                        )
                )
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    data.geometry.coordinates.get(0), data.geometry.coordinates.get(0)
                ), 12.0f
            )
        )
    }
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}