package com.clementecastillo.people.client

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.core.client.transaction.TransactionRequest
import com.clementecastillo.people.BuildConfig
import io.reactivex.Observable
import io.reactivex.Single

class TransactionRequestImpl : TransactionRequest {

    override fun <T> wrap(observable: Observable<out T>): Observable<Transaction<T>> {
        return observable.map<Transaction<T>> {
            Transaction.Success(it)
        }.doOnError {
            if (BuildConfig.DEBUG) {
                it.printStackTrace()
            }
        }.onErrorResumeNext { throwable: Throwable ->
            Observable.just(Transaction.Fail(throwable))
        }
    }

    override fun <T> wrap(single: Single<out T>): Single<Transaction<T>> {
        return single.map<Transaction<T>> {
            Transaction.Success(it)
        }.doOnError {
            if (BuildConfig.DEBUG) {
                it.printStackTrace()
            }
        }.onErrorResumeNext { throwable: Throwable ->
            Single.just(Transaction.Fail(throwable))
        }
    }

}
