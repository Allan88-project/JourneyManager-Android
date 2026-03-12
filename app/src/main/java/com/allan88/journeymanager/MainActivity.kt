package com.allan88.journeymanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.network.TokenManager
import com.allan88.journeymanager.ui.admin.AdminTripScreen
import com.allan88.journeymanager.ui.trip.TripScreen
import com.allan88.journeymanager.ui.common.MenuScreen
import kotlinx.coroutines.launch
import com.allan88.journeymanager.ui.common.RoleSelectionScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

            try {

                // Login request
                val token = ApiClient.apiService.login(
                    mapOf(
                        "email" to "admin@tenant1.com",
                        "password" to "password"
                    )
                )

                Log.d("AUTH", "TOKEN RECEIVED: $token")

                if (!token.isNullOrBlank()) {

                    TokenManager.saveToken(token)

                    Log.d("AUTH", "TOKEN SAVED SUCCESSFULLY")

                } else {

                    Log.e("AUTH", "TOKEN IS NULL OR EMPTY")

                }

            } catch (e: Exception) {

                Log.e("AUTH", "Login failed", e)

            }

            // Load UI after login attempt
            setContent {
                MainMenu()
            }
        }
    }
}

@Composable
fun MainMenu() {

    var screen by remember { mutableStateOf("role") }

    when (screen) {

        "role" -> RoleSelectionScreen(
            onUserSelected = { screen = "userTrips" },
            onAdminSelected = { screen = "adminTrips" }
        )

        "userTrips" -> TripScreen(
            onBack = { screen = "role" }
        )

        "adminTrips" -> AdminTripScreen(
            onBack = { screen = "role" }
        )
    }
}