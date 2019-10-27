package com.clementecastillo.core.domain.provider

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.core.domain.repository.people.PeopleApiRepository
import com.clementecastillo.core.domain.repository.people.PeopleCacheRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleProvider @Inject constructor(
    private val peopleApiRepository: PeopleApiRepository,
    private val peopleCacheRepository: PeopleCacheRepository
) {

    fun getPeople(): Single<Transaction<List<Person>>> {
        return peopleCacheRepository.load().switchIfEmpty(peopleApiRepository.getPeople().doOnSuccess {
            if (it is Transaction.Success) {
                peopleCacheRepository.save(it.data).subscribe()
            }
        })
    }
}