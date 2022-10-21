package com.appsuite.earn.money.playing.bikestationapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.appsuite.earn.money.playing.bikestationapp.Constants
import com.appsuite.earn.money.playing.bikestationapp.R
import com.appsuite.earn.money.playing.bikestationapp.model.BikesStation
import com.appsuite.earn.money.playing.bikestationapp.ui.DetailStationActivity
import kotlin.math.roundToInt

class AdapterStations(context: Context, list: BikesStation) :
    RecyclerView.Adapter<AdapterStations.CustomStationView>() {
    private var context: Context
    private var data: BikesStation

    init {
        this.context = context
        this.data = list
    }

    class CustomStationView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var labelStation = itemView.findViewById<TextView>(R.id.tv_label_parking)
        var availablePlaces = itemView.findViewById<TextView>(R.id.available_places)
        var availableBikes = itemView.findViewById<TextView>(R.id.tv_available_bikes)
        var distanceFar = itemView.findViewById<TextView>(R.id.tv_distance)
        var cardView = itemView.findViewById<CardView>(R.id.carview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomStationView {
        var view = LayoutInflater.from(context).inflate(R.layout.custom_bike_places, parent, false)
        return CustomStationView(view)
    }

    override fun onBindViewHolder(holder: CustomStationView, position: Int) {
        holder.labelStation.setText(data.features.get(position).properties.label)
        holder.availablePlaces.setText(data.features.get(position).properties.free_racks)
        holder.availableBikes.setText(data.features.get(position).properties.bikes)
        var distance = -1
        if (Constants.location != null) {
            var myLocation = Constants.location
            var distance = Constants.distance(
                myLocation!!.latitude,
                myLocation.longitude,
                data.features.get(position).geometry.coordinates.get(0),
                data.features.get(position).geometry.coordinates.get(1)
            )
            distance = distance * 1.6;
            distance = (distance * 100.0).roundToInt() / 100.0
            holder.distanceFar.setText("$distance km-bikeStation")
        }
        holder.cardView.setOnClickListener {
            var internt = Intent(context, DetailStationActivity::class.java)
            internt.putExtra("data", data.features.get(position))
            context.startActivity(internt)
        }

    }

    override fun getItemCount(): Int {
        return data.features.size
    }
}