package com.appsuite.earn.money.playing.bikestationapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BikesStation(
    val crs: Crs,
    val features: List<Feature>,
    val type: String
):Parcelable