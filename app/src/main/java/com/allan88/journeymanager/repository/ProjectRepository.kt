package com.allan88.journeymanager.repository

import com.allan88.journeymanager.data.model.ProjectResponse
import com.allan88.journeymanager.network.ApiClient

class ProjectRepository {
    suspend fun createProject(name: String): ProjectResponse? {
        val response = ApiClient.apiService.createProject(mapOf("name" to name))

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
    suspend fun getProjects(): List<ProjectResponse>? {
        val response = ApiClient.apiService.getProjects()

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}