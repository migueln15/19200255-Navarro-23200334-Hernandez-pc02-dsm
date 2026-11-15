package com.example.dampc2.asteroides.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/** Endpoint Feed de NeoWs. El api_key lo agrega RetrofitClient. */
interface NeoWsApi {

    @GET("neo/rest/v1/feed")
    suspend fun obtenerFeed(
        @Query("start_date") fechaInicio: String,
        @Query("end_date") fechaFin: String
    ): NeoFeedDto
}
