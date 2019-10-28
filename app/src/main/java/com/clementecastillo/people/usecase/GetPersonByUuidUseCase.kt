package com.clementecastillo.people.usecase

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.core.domain.provider.PeopleProvider
import com.clementecastillo.people.extension.mainThread
import com.clementecastillo.people.usecase.common.UseCaseWithParams
import io.reactivex.Single
import javax.inject.Inject

class GetPersonByUuidUseCase @Inject constructor(private val peopleProvider: PeopleProvider) : UseCaseWithParams<Single<Transaction<Person>>, GetPersonByUuidUseCase.Params> {

    override fun bind(params: Params): Single<Transaction<Person>> {
        return peopleProvider.getPersonByUuid(params.personUuid).mainThread()
    }

    class Params(val personUuid: String)
}