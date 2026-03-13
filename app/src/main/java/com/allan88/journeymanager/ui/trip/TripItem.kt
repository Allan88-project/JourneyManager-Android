package com.allan88.journeymanager.ui.trip

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.viewmodel.TripViewModel

@Composable
fun TripItem(
    trip: Trip,
    viewModel: TripViewModel,
    isAdmin: Boolean
) {

    val context = LocalContext.current

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

            /*
             * =========================
             * ADMIN ACTIONS
             * =========================
             */

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

            /*
             * =========================
             * USER ACTIONS
             * =========================
             */

            if (!isAdmin && trip.status == "APPROVED") {

                Button(
                    onClick = {

                        trip.id?.let { id ->

                            viewModel.startJourney(id)

                            val lat = trip.destinationLatitude
                            val lon = trip.destinationLongitude

                            Log.d("NAVIGATION", "LAT=$lat LON=$lon")

                            if (lat != null && lon != null) {

                                openGoogleMaps(context, lat, lon)

                            } else {

                                Log.e("NAVIGATION", "Trip has no coordinates")

                            }
                        }
                    }
                ) {
                    Text("Start Journey")
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            /*
             * =========================
             * TRIP IN PROGRESS
             * =========================
             */

            if (!isAdmin && trip.status == "IN_PROGRESS") {

                Column {

                    Button(
                        onClick = {

                            val lat = trip.destinationLatitude
                            val lon = trip.destinationLongitude

                            if (lat != null && lon != null) {

                                openGoogleMaps(context, lat, lon)

                            } else {

                                Log.e("NAVIGATION", "Trip has no coordinates")

                            }
                        }
                    ) {
                        Text("Resume Navigation")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

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
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            /*
             * =========================
             * FINAL STATES
             * =========================
             */

            if (trip.status == "COMPLETED") {
                Text("Journey Completed")
            }

            if (trip.status == "REJECTED") {
                Text("Trip Rejected")
            }
        }
    }

}

/*

* Launch Google Maps navigation
  */

fun openGoogleMaps(
    context: Context,
    lat: Double,
    lng: Double
) {

    val uri = Uri.parse("google.navigation:q=$lat,$lng")

    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")

    if (intent.resolveActivity(context.packageManager) != null) {

        context.startActivity(intent)

    } else {

        val fallback = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat,$lng")
        )

        context.startActivity(fallback)
    }

}
