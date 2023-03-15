package com.example.roomtesting.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("todos/")
    suspend fun getAllUser():List<UserDto>

    @GET("todos/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDto
}