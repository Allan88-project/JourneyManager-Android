package com.allan88.journeymanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allan88.journeymanager.R
import com.allan88.journeymanager.data.model.Trip

class TripAdapter(
    private val onApprove: (Trip) -> Unit,
    private val onReject: (Trip) -> Unit
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private var items: List<Trip> = emptyList()

    fun submitList(data: List<Trip>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(items[position], onApprove, onReject)
    }

    override fun getItemCount(): Int = items.size

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.textProjectName)
        private val status: TextView = itemView.findViewById(R.id.textProjectStatus)
        private val actionContainer: LinearLayout =
            itemView.findViewById(R.id.actionContainer)
        private val approveButton: Button =
            itemView.findViewById(R.id.buttonApprove)
        private val rejectButton: Button =
            itemView.findViewById(R.id.buttonReject)

        fun bind(
            trip: Trip,
            onApprove: (Trip) -> Unit,
            onReject: (Trip) -> Unit
        ) {
            title.text = trip.title
            status.text = trip.status

            when (trip.status) {
                "APPROVED" -> {
                    status.setTextColor(
                        itemView.context.getColor(android.R.color.holo_green_dark)
                    )
                    actionContainer.visibility = View.GONE
                }
                "REJECTED" -> {
                    status.setTextColor(
                        itemView.context.getColor(android.R.color.holo_red_dark)
                    )
                    actionContainer.visibility = View.GONE
                }
                "PENDING" -> {
                    status.setTextColor(
                        itemView.context.getColor(android.R.color.holo_orange_dark)
                    )
                    actionContainer.visibility = View.VISIBLE

                    approveButton.setOnClickListener {
                        onApprove(trip)
                    }

                    rejectButton.setOnClickListener {
                        onReject(trip)
                    }
                }
                else -> {
                    actionContainer.visibility = View.GONE
                }
            }
        }
    }
}