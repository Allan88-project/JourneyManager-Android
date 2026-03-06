package com.allan88.journeymanager.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.allan88.journeymanager.databinding.ActivityAdminTripsBinding
import com.allan88.journeymanager.network.ApiClient
import com.allan88.journeymanager.data.repository.TripRepository
import com.allan88.journeymanager.viewmodel.AdminTripViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdminTripScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAdminTripsBinding
    private lateinit var viewModel: AdminTripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminTripsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = TripRepository(ApiClient.apiService)
        viewModel = AdminTripViewModel(repository)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.trips.collectLatest { trips ->

                binding.recyclerView.adapter =
                    AdminTripAdapter(
                        trips = trips,
                        onApprove = { trip ->
                            trip.id?.let { viewModel.approveTrip(it) }
                        },
                        onReject = { trip ->
                            trip.id?.let { viewModel.rejectTrip(it) }
                        }
                    )
            }
        }

        viewModel.loadTrips()
    }
}