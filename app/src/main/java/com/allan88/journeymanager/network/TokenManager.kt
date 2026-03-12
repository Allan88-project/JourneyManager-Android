package com.allan88.journeymanager.network

object TokenManager {

    private var token: String? = null

    fun saveToken(jwt: String) {
        token = jwt
    }

    fun getToken(): String? {
        return token
    }
}