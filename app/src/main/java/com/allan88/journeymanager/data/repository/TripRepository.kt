package com.allan88.journeymanager.data.repository

import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.data.remote.TripApi

class TripRepository(
    private val api: TripApi
) {

    suspend fun getTrips(): List<Trip> {
        return api.getTrips()
    }

    suspend fun createTrip(title: String, description: String): Trip {
        return api.createTrip(
            mapOf(
                "title" to title,
                "description" to description
            )
        )
    }

    suspend fun updateStatus(id: Long, status: String): Trip {
        return api.updateStatus(id, status)
    }
}