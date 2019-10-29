package com.clementecastillo.people.usecase

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.core.client.transaction.mapSuccess
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.core.domain.provider.PeopleProvider
import com.clementecastillo.people.extension.mainThread
import com.clementecastillo.people.usecase.common.UseCase
import io.reactivex.Single
import javax.inject.Inject

class GetPeopleUseCase @Inject constructor(private val peopleProvider: PeopleProvider) : UseCase<Single<Transaction<List<Person>>>> {

    override fun bind(): Single<Transaction<List<Person>>> {
        return peopleProvider.getPeople().map {
            it.mapSuccess { it.distinctBy { it.personId } }
        }.mainThread()
    }

}