package com.clementecastillo.people.client.api

import com.clementecastillo.core.client.ApiClient
import com.clementecastillo.core.domain.data.Person
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PeopleApiClient(private val restApiClient: RestApiClient) : ApiClient {

    companion object {
        private const val PAGE_ITEM_COUNT = 10
    }

    private fun getPageNumber(itemCount: Int): Int {
        return itemCount / PAGE_ITEM_COUNT
    }

    override fun getPeople(currentItemCount: Int): Single<List<Person>> {
        return restApiClient.getPeople(getPageNumber(currentItemCount), PAGE_ITEM_COUNT).map {
            it.peopleList
        }.execute()
    }

    private fun <T> Single<out T>.execute(): Single<T> {
        return this.subscribeOn(Schedulers.io()).map { it }
    }
}