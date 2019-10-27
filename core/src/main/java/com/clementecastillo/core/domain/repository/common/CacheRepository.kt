package com.clementecastillo.core.domain.repository.common

import com.clementecastillo.core.client.transaction.Transaction
import io.reactivex.Completable
import io.reactivex.Maybe

abstract class CacheRepository<T>(var validityTime: Long = VALIDITY_LONG_TIME) {

    companion object {
        const val VALIDITY_LONG_TIME = 1200000L
        const val VALIDITY_SHORT_TIME = 150000L
    }

    private var data: T? = null
    private var timestamp: Long = 0

    open fun load(): Maybe<Transaction<T>> {
        return Maybe.create { subscriber ->
            if (data != null && isValidTime()) {
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
            updateTimestamp()
            subscriber.onComplete()
        }
    }

    fun clear(): Completable {
        return Completable.create { subscriber ->
            this.data = null
            subscriber.onComplete()
        }
    }

    private fun updateTimestamp() {
        timestamp = System.currentTimeMillis()
    }

    private fun isValidTime(): Boolean {
        return System.currentTimeMillis() - timestamp < validityTime
    }
}