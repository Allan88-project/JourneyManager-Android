package com.allan88.journeymanager.network

import com.allan88.journeymanager.data.model.ProjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
interface ApiService {
    @POST("api/projects")
    suspend fun createProject(@Body project: Map<String, String>): Response<ProjectResponse>
    @GET("api/projects")
    suspend fun getProjects(): Response<List<ProjectResponse>>
}