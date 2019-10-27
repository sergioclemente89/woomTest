package com.clementecastillo.people.client.api

import com.clementecastillo.people.client.api.data.GetPeopleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiClient {

    companion object {
        const val HOST = "https://api.randomuser.me"
    }

    @GET("/")
    fun getPeople(@Query("page") page: Int, @Query("results") pageCount: Int): Single<GetPeopleResponse>
}