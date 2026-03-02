package com.allan88.journeymanager.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allan88.journeymanager.R
import com.allan88.journeymanager.data.model.ProjectResponse

class ProjectAdapter : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var projects: List<ProjectResponse> = emptyList()

    fun submitList(newList: List<ProjectResponse>) {
        projects = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(view as TextView)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(projects[position])
    }

    override fun getItemCount(): Int = projects.size

    class ProjectViewHolder(private val textView: TextView) :
        RecyclerView.ViewHolder(textView) {

        fun bind(project: ProjectResponse) {
            textView.text = project.name
        }
    }
}