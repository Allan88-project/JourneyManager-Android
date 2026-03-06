package com.allan88.journeymanager.network

import com.allan88.journeymanager.data.model.Trip
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * Fetch all trips
     */
    @GET("api/trips")
    suspend fun getTrips(): List<Trip>


    /**
     * Submit a new trip
     */
    @POST("api/trips")
    suspend fun createTrip(
        @Body request: Map<String, String>
    )


    /**
     * Start journey
     */
    @PUT("api/trips/{id}/start")
    suspend fun startJourney(
        @Path("id") tripId: Long
    )


    /**
     * Trigger emergency
     */
    @PUT("api/trips/{id}/emergency")
    suspend fun emergency(
        @Path("id") tripId: Long
    )


    /**
     * Complete journey
     */
    @PUT("api/trips/{id}/complete")
    suspend fun completeJourney(
        @Path("id") tripId: Long
    )


    /**
     * Approve / Reject trip
     */
    @PUT("api/trips/{id}/status")
    suspend fun updateStatus(
        @Path("id") tripId: Long,
        @Query("status") status: String
    )
}