package com.clementecastillo.core.client.transaction

import io.reactivex.Observable
import io.reactivex.Single

interface TransactionRequest {

    fun <T> wrap(observable: Observable<out T>): Observable<Transaction<T>>

    fun <T> wrap(single: Single<out T>): Single<Transaction<T>>

}