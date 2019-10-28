package com.clementecastillo.core.domain.provider

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.core.domain.repository.people.PeopleApiRepository
import com.clementecastillo.core.domain.repository.people.PeopleCacheRepository
import io.reactivex.Completable
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

    fun getMorePeople(currentItemCount: Int): Single<Transaction<List<Person>>> {
        return peopleApiRepository.getPeople(currentItemCount).doOnSuccess { newPeopleTransaction ->
            when (newPeopleTransaction) {
                is Transaction.Success -> {
                    peopleCacheRepository.load().flatMapCompletable {
                        if (it is Transaction.Success) {
                            val cachedPeple = it.data.toMutableList()
                            cachedPeple.addAll(newPeopleTransaction.data)
                            peopleCacheRepository.save(cachedPeple)
                        } else {
                            Completable.complete()
                        }
                    }.subscribe()
                }
                is Transaction.Fail -> {

                }
            }
        }
    }

    fun getPersonByUuid(personUuid: String): Single<Transaction<Person>> {
        return peopleCacheRepository.load().map<Transaction<Person>> {
            if (it is Transaction.Success) {
                val foundPerson = it.data.find { it.personId.uuid == personUuid }
                if (foundPerson == null) {
                    Transaction.Fail()
                } else {
                    Transaction.Success(foundPerson)
                }
            } else {
                Transaction.Fail()
            }

        }.switchIfEmpty(Single.just(Transaction.Fail()))
    }
}