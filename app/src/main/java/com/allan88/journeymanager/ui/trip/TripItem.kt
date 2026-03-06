package com.allan88.journeymanager.ui.trip

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.viewmodel.TripViewModel

@Composable
fun TripItem(
    trip: Trip,
    viewModel: TripViewModel
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(text = trip.title)
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = trip.description ?: "")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Status: ${trip.status}")
            Spacer(modifier = Modifier.height(12.dp))

            // Start Journey
            if (trip.status == "APPROVED") {
                Button(
                    onClick = {
                        trip.id?.let { id ->
                            viewModel.startJourney(id)
                        }
                    }
                ) {
                    Text("Start Journey")
                }
            }

            // Journey In Progress
            if (trip.status == "IN_PROGRESS") {

                Button(
                    onClick = {
                        trip.id?.let { id ->
                            viewModel.emergency(id)
                        }
                    }
                ) {
                    Text("Emergency")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        trip.id?.let { id ->
                            viewModel.completeJourney(id)
                        }
                    }
                ) {
                    Text("End Journey")
                }
            }

            // Completed state
            if (trip.status == "COMPLETED") {
                Text("Journey Completed")
            }
        }
    }
}