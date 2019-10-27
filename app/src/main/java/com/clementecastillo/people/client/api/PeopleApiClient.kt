package com.clementecastillo.people.client.api

import com.clementecastillo.core.client.ApiClient
import com.clementecastillo.core.domain.data.Person
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PeopleApiClient(private val restApiClient: RestApiClient) : ApiClient {

    override fun getPeople(): Single<List<Person>> {
        return restApiClient.getPeople(0, 10).map {
            it.peopleList
        }.execute()
    }

    private fun <T> Single<out T>.execute(): Single<T> {
        return this.subscribeOn(Schedulers.io()).map { it }
    }
}