package com.clementecastillo.core.domain.repository.people

import com.clementecastillo.core.client.ApiClient
import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.core.client.transaction.TransactionRequest
import com.clementecastillo.core.domain.data.Person
import io.reactivex.Single
import javax.inject.Inject

class PeopleApiRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val transactionRequest: TransactionRequest
) {

    fun getPeople(currentItemCount: Int = 0): Single<Transaction<List<Person>>> {
        return transactionRequest.wrap(apiClient.getPeople(currentItemCount))
    }
}