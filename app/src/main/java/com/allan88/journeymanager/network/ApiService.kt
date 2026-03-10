package com.allan88.journeymanager.network

import com.allan88.journeymanager.data.model.Trip
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    /**
     * LOGIN (returns JWT token string)
     */
    @POST("api/auth/login")
    suspend fun login(
        @Body request: Map<String, String>
    ): String


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
    ): Trip


    /**
     * Start journey
     */
    @PUT("api/trips/{id}/start")
    suspend fun startJourney(
        @Path("id") tripId: Long
    ): Trip


    /**
     * Trigger emergency
     */
    @PUT("api/trips/{id}/emergency")
    suspend fun emergency(
        @Path("id") tripId: Long
    ): Trip


    /**
     * Complete journey
     */
    @PUT("api/trips/{id}/complete")
    suspend fun completeJourney(
        @Path("id") tripId: Long
    ): Trip


    /**
     * ADMIN approve trip
     */
    @PUT("api/trips/{id}/approve")
    suspend fun approveTrip(
        @Path("id") tripId: Long
    ): Trip


    /**
     * ADMIN reject trip
     */
    @PUT("api/trips/{id}/reject")
    suspend fun rejectTrip(
        @Path("id") tripId: Long
    ): Trip
}