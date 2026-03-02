package com.allan88.journeymanager

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.allan88.journeymanager.data.remote.RetrofitClient
import com.allan88.journeymanager.data.repository.TripRepository
import com.allan88.journeymanager.ui.TripAdapter
import com.allan88.journeymanager.ui.common.UiState
import com.allan88.journeymanager.viewmodel.TripViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var emptyContainer: View
    private lateinit var retryButton: android.widget.Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton
    private lateinit var emptyStateText: TextView
    private lateinit var adapter: TripAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var viewModel: TripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emptyContainer = findViewById(R.id.emptyContainer)
        retryButton = findViewById(R.id.buttonRetry)
        recyclerView = findViewById(R.id.recyclerViewProjects)
        progressBar = findViewById(R.id.progressBar)
        fab = findViewById(R.id.fabAddProject)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        emptyStateText = findViewById(R.id.textEmptyState)

        setupViewModel()
        setupRecyclerView()
        observeViewModel()

        retryButton.setOnClickListener {
            viewModel.loadTrips()
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.loadTrips()
        }

        fab.setOnClickListener {
            showCreateTripDialog()
        }

        viewModel.loadTrips()
    }

    private fun setupViewModel() {
        val repository = TripRepository(RetrofitClient.tripApi)

        viewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return TripViewModel(repository) as T
                }
            }
        )[TripViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = TripAdapter(
            onApprove = { trip ->
                trip.id?.let { viewModel.updateStatus(it, "APPROVED") }
            },
            onReject = { trip ->
                trip.id?.let { viewModel.updateStatus(it, "REJECTED") }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.tripsState.observe(this) { state ->
            when (state) {

                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    swipeRefresh.isRefreshing = true
                    fab.isEnabled = false
                    emptyContainer.visibility = View.GONE
                }

                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                    fab.isEnabled = true

                    adapter.submitList(state.data)

                    if (state.data.isEmpty()) {
                        recyclerView.visibility = View.GONE
                        emptyContainer.visibility = View.VISIBLE
                        retryButton.visibility = View.GONE
                        emptyStateText.text =
                            "No trips yet.\nTap + to submit your first trip."
                    } else {
                        emptyContainer.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }

                is UiState.Error -> {
                    progressBar.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                    fab.isEnabled = true

                    recyclerView.visibility = View.GONE
                    emptyContainer.visibility = View.VISIBLE
                    retryButton.visibility = View.VISIBLE
                    emptyStateText.text =
                        "Something went wrong.\nPlease try again."

                    Toast.makeText(
                        this,
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {
                    // Idle or future states
                }
            }
        }
    }

    private fun showCreateTripDialog() {
        val input = EditText(this)
        input.hint = "Trip Title"

        AlertDialog.Builder(this)
            .setTitle("Submit Trip")
            .setView(input)
            .setPositiveButton("Submit") { _, _ ->
                val title = input.text.toString().trim()
                if (title.isNotEmpty()) {
                    viewModel.submitTrip(title, "Submitted from Android")
                } else {
                    Toast.makeText(
                        this,
                        "Trip title cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}