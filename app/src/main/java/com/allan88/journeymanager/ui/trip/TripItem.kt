package com.allan88.journeymanager.ui.trip

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.viewmodel.TripViewModel

@Composable
fun TripItem(
    trip: Trip,
    viewModel: TripViewModel,
    isAdmin: Boolean
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(text = trip.title ?: "Untitled Trip")

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = trip.description ?: "")

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Status: ${trip.status}")

            Spacer(modifier = Modifier.height(12.dp))

            // =========================
            // ADMIN ACTIONS
            // =========================

            if (isAdmin && trip.status == "PENDING") {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            trip.id?.let { id ->
                                viewModel.approveTrip(id)
                            }
                        }
                    ) {
                        Text("Approve")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            trip.id?.let { id ->
                                viewModel.rejectTrip(id)
                            }
                        }
                    ) {
                        Text("Reject")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            // =========================
            // USER ACTIONS
            // =========================

            if (!isAdmin && trip.status == "APPROVED") {

                Button(
                    onClick = {
                        trip.id?.let { id ->
                            viewModel.startJourney(id)
                        }
                    }
                ) {
                    Text("Start Journey")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (!isAdmin && trip.status == "IN_PROGRESS") {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            trip.id?.let { id ->
                                viewModel.emergency(id)
                            }
                        }
                    ) {
                        Text("Emergency")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

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

                Spacer(modifier = Modifier.height(8.dp))
            }

            // =========================
            // FINAL STATES
            // =========================

            if (trip.status == "COMPLETED") {
                Text("Journey Completed")
            }

            if (trip.status == "REJECTED") {
                Text("Trip Rejected")
            }
        }
    }
}