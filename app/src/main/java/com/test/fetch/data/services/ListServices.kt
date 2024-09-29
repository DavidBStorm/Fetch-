package com.test.fetch.data.services

import com.test.fetch.domain.model.FetchItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ListServices {

    interface ListServices {
        @GET("hiring.json")
        suspend fun getFetchItems(): List<FetchItem>
    }



}