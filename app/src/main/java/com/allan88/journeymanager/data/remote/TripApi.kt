package com.allan88.journeymanager.data.remote

import com.allan88.journeymanager.data.model.Trip
import retrofit2.http.*

interface TripApi {

    @GET("api/trips")
    suspend fun getTrips(): List<Trip>

    @POST("api/trips")
    suspend fun createTrip(
        @Body body: Map<String, String>
    ): Trip

    @PUT("api/trips/{id}/status")
    suspend fun updateStatus(
        @Path("id") id: Long,
        @Query("status") status: String
    ): Trip
}