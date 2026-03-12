package com.allan88.journeymanager.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.data.repository.TripRepository
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.viewmodel.TripViewModel
import com.allan88.journeymanager.ui.trip.TripItem
import kotlinx.coroutines.delay

@Composable
fun AdminTripScreen(
    onBack: () -> Unit
) {

    val repository = remember {
        TripRepository(ApiClient.apiService)
    }

    val viewModel = remember {
        TripViewModel(repository)
    }

    val trips by viewModel.trips.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.loadTrips()

        while (true) {
            delay(3000)
            viewModel.loadTrips()
        }
    }

    val pending = trips.count { it.status == "PENDING" }
    val approved = trips.count { it.status == "APPROVED" }
    val inProgress = trips.count { it.status == "IN_PROGRESS" }
    val completed = trips.count { it.status == "COMPLETED" }
    val emergency = trips.count { it.status == "EMERGENCY" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Button(onClick = onBack) {
            Text("BACK")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "ADMIN PANEL",
            color = Color.Red,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Trips Loaded: ${trips.size}",
            color = Color.Green
        )

        Spacer(modifier = Modifier.height(20.dp))

        // =========================
        // DASHBOARD
        // =========================

        DashboardCard("Pending", pending)
        DashboardCard("Approved", approved)
        DashboardCard("In Progress", inProgress)
        DashboardCard("Completed", completed)
        DashboardCard("Emergency", emergency)

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(trips) { trip: Trip ->

                TripItem(
                    trip = trip,
                    viewModel = viewModel,
                    isAdmin = true
                )

            }

        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(title)

            Text(value.toString())

        }

    }
}