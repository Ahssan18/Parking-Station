package com.appsuite.earn.money.playing.bikestationapp.networkinf

import com.appsuite.earn.money.playing.bikestationapp.model.BikesStation
import retrofit2.Response
import retrofit2.http.GET

interface BikesStationApi {
    @GET("map_service.html?mtype=pub_transport&co=stacje_rowerowe")
    suspend fun getBikeStation(): Response<BikesStation>
}