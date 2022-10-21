package com.appsuite.earn.money.playing.bikestationapp.networkinf

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val TAG = "--ApiService"

    fun getBikeStation() = Retrofit.Builder()
        .baseUrl("http://www.poznan.pl/mim/plan/")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build().create(BikesStationApi::class.java)!!
}