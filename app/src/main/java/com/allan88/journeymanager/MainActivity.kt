package com.allan88.journeymanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.network.TokenManager
import com.allan88.journeymanager.ui.admin.AdminTripScreen
import com.allan88.journeymanager.ui.trip.TripScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var btnSubmitTrip: Button
    private lateinit var btnViewTrips: Button
    private lateinit var btnAdminPanel: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSubmitTrip = findViewById(R.id.btnSubmitTrip)
        btnViewTrips = findViewById(R.id.btnViewTrips)
        btnAdminPanel = findViewById(R.id.btnAdminPanel)

        // Disable buttons until login completes
        btnSubmitTrip.isEnabled = false
        btnViewTrips.isEnabled = false
        btnAdminPanel.isEnabled = false

        loginThenEnableUI()
    }

    private fun loginThenEnableUI() {

        lifecycleScope.launch {

            try {

                val token = withContext(Dispatchers.IO) {
                    ApiClient.apiService.login(
                        mapOf(
                            "email" to "user@tenant1.com",
                            "password" to "password"
                        )
                    )
                }

                TokenManager.saveToken(token)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Enable buttons after login attempt
            btnSubmitTrip.isEnabled = true
            btnViewTrips.isEnabled = true
            btnAdminPanel.isEnabled = true

            btnSubmitTrip.setOnClickListener {
                setContent {
                    TripScreen(onBack = { recreate() })
                }
            }

            btnViewTrips.setOnClickListener {
                setContent {
                    TripScreen(onBack = { recreate() })
                }
            }

            btnAdminPanel.setOnClickListener {
                startActivity(Intent(this@MainActivity, AdminTripScreen::class.java))
            }
        }
    }

}
