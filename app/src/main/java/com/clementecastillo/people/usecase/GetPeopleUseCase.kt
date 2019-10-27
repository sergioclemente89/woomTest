package com.clementecastillo.people.usecase

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.core.client.transaction.mapSuccess
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.core.domain.provider.PeopleProvider
import com.clementecastillo.people.extension.mainThread
import com.clementecastillo.people.usecase.common.UseCaseWithParams
import io.reactivex.Single
import javax.inject.Inject

class GetPeopleUseCase @Inject constructor(private val peopleProvider: PeopleProvider) : UseCaseWithParams<Single<Transaction<List<Person>>>, GetPeopleUseCase.Params> {

    override fun bind(params: Params): Single<Transaction<List<Person>>> {
        return peopleProvider.getPeople().map {
            it.mapSuccess { it.distinctBy { it.personId } }
        }.mainThread()
    }

    // TODO: Request more page results
    class Params(val currentItemCount: Int)
}