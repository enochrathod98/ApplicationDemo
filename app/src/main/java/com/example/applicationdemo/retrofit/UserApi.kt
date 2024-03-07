package com.example.applicationdemo.retrofit

import com.example.applicationdemo.models.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun getUsers(): UserResponse
}