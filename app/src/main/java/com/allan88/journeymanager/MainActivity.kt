package com.allan88.journeymanager
import com.allan88.journeymanager.ui.trip.TripScreen
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.allan88.journeymanager.ui.admin.AdminTripScreen
import com.allan88.journeymanager.ui.trip.TripScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSubmitTrip = findViewById<Button>(R.id.btnSubmitTrip)
        val btnViewTrips = findViewById<Button>(R.id.btnViewTrips)
        val btnAdminPanel = findViewById<Button>(R.id.btnAdminPanel)

        btnSubmitTrip.setOnClickListener {

            setContent {

                TripScreen(
                    onBack = {
                        recreate()
                    }
                )

            }

        }

        btnViewTrips.setOnClickListener {

            setContent {
                TripScreen(
                    onBack = {
                        recreate()
                    }
                )
            }

        }

        btnAdminPanel.setOnClickListener {
            startActivity(Intent(this, AdminTripScreen::class.java))
        }
    }
}