package com.appsuite.earn.money.playing.bikestationapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Feature (
    val geometry: Geometry,
    val id: String,
    val properties: PropertiesX,
    val type: String
):Parcelable