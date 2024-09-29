package com.test.fetch.data.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListServices {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("title") title: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<String>

}