package com.clementecastillo.core.domain.repository.common

import com.clementecastillo.core.client.transaction.Transaction
import io.reactivex.Completable
import io.reactivex.Maybe

abstract class CacheRepository<T> {

    private var data: T? = null

    open fun load(): Maybe<Transaction<T>> {
        return Maybe.create { subscriber ->
            if (data != null) {
                subscriber.onSuccess(Transaction.Success(data!!))
            } else {
                data = null
            }
            subscriber.onComplete()
        }
    }

    fun save(value: T): Completable {
        return Completable.create { subscriber ->
            this.data = value
            subscriber.onComplete()
        }
    }

    fun clear(): Completable {
        return Completable.create { subscriber ->
            this.data = null
            subscriber.onComplete()
        }
    }
}