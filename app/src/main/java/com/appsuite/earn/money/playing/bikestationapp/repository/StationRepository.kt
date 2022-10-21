package com.appsuite.earn.money.playing.bikestationapp.repository

import com.appsuite.earn.money.playing.bikestationapp.networkinf.ApiService

class StationRepository {
    suspend fun getStations() = ApiService.getBikeStation().getBikeStation()
}