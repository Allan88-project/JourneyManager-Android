package com.allan88.journeymanager.data.repository

import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.network.ApiService

class TripRepository(private val apiService: ApiService) {

    suspend fun getTrips(): List<Trip> {
        return apiService.getTrips()
    }

    suspend fun submitTrip(data: Map<String, String>) {
        apiService.createTrip(data)
    }

    suspend fun startJourney(tripId: Long) {
        apiService.startJourney(tripId)
    }

    suspend fun emergency(tripId: Long) {
        apiService.emergency(tripId)
    }

    suspend fun completeJourney(tripId: Long) {
        apiService.completeJourney(tripId)
    }

    suspend fun approveTrip(tripId: Long) {
        apiService.updateStatus(tripId, "APPROVED")
    }

    suspend fun rejectTrip(tripId: Long) {
        apiService.updateStatus(tripId, "REJECTED")
    }
}