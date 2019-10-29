package com.clementecastillo.core.client

import com.clementecastillo.core.domain.data.Person
import io.reactivex.Single

interface ApiClient {

    fun getPeople(currentItemCount: Int): Single<List<Person>>
}