package com.allan88.journeymanager.data.model

data class Trip(
    val id: Long? = null,
    val tenantId: Long? = null,
    val title: String,
    val description: String,
    val status: String,
    val createdAt: String? = null
)