package com.appsuite.earn.money.playing.bikestationapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Crs(
    val properties: Properties,
    val type: String
):Parcelable