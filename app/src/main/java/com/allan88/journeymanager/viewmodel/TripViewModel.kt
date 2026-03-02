package com.allan88.journeymanager.viewmodel

import androidx.lifecycle.*
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.data.repository.TripRepository
import com.allan88.journeymanager.ui.common.UiState
import kotlinx.coroutines.launch

class TripViewModel(
    private val repository: TripRepository
) : ViewModel() {

    private val _tripsState = MutableLiveData<UiState<List<Trip>>>()
    val tripsState: LiveData<UiState<List<Trip>>> = _tripsState

    fun loadTrips() {
        viewModelScope.launch {
            _tripsState.value = UiState.Loading
            try {
                val trips = repository.getTrips()
                _tripsState.value = UiState.Success(trips)
            } catch (e: Exception) {
                _tripsState.value =
                    UiState.Error(e.message ?: "Failed to load trips")
            }
        }
    }

    fun submitTrip(title: String, description: String) {
        viewModelScope.launch {
            try {
                repository.createTrip(title, description)
                loadTrips()
            } catch (e: Exception) {
                _tripsState.value =
                    UiState.Error(e.message ?: "Submission failed")
            }
        }
    }

    fun updateStatus(id: Long, status: String) {
        viewModelScope.launch {
            try {
                repository.updateStatus(id, status)
                loadTrips()
            } catch (e: Exception) {
                _tripsState.value =
                    UiState.Error(e.message ?: "Status update failed")
            }
        }
    }
}