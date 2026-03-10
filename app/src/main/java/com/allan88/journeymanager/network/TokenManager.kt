package com.allan88.journeymanager.network

object TokenManager {

    private var jwt: String? = null

    fun saveToken(token: String) {
        jwt = token
    }

    fun getToken(): String? {
        return jwt
    }
}