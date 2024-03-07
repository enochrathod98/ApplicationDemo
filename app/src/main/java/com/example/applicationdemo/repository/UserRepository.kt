package com.example.applicationdemo.repository

import com.example.applicationdemo.models.UserResponse
import com.example.applicationdemo.retrofit.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    suspend fun getUsers(): UserResponse {
        return userApi.getUsers()
    }
}