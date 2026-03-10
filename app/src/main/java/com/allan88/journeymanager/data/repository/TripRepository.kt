package com.allan88.journeymanager.data.repository

import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.network.ApiService

class TripRepository(private val apiService: ApiService) {

    suspend fun getTrips(): List<Trip> {
        return apiService.getTrips()
    }

    suspend fun submitTrip(data: Map<String, String>): Trip {
        return apiService.createTrip(data)
    }

    suspend fun startJourney(tripId: Long): Trip {
        return apiService.startJourney(tripId)
    }

    suspend fun emergency(tripId: Long): Trip {
        return apiService.emergency(tripId)
    }

    suspend fun completeJourney(tripId: Long): Trip {
        return apiService.completeJourney(tripId)
    }

    suspend fun approveTrip(tripId: Long): Trip {
        return apiService.approveTrip(tripId)
    }

    suspend fun rejectTrip(tripId: Long): Trip {
        return apiService.rejectTrip(tripId)
    }
}