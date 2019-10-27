package com.clementecastillo.core.client.transaction

sealed class Transaction<T> {
    class Success<T>(val data: T) : Transaction<T>()
    class Fail<T>(val throwable: Throwable? = null) : Transaction<T>()
}

fun Transaction<*>.isSuccess(): Boolean {
    return this is Transaction.Success<*>
}

fun <T, R> Transaction<T>.mapSuccess(function: (T) -> R): Transaction<R> {
    return when (this) {
        is Transaction.Success<T> -> Transaction.Success(function(data))
        is Transaction.Fail<T> -> this.mapError()
    }
}

fun <T, R> Transaction.Fail<T>.mapError(): Transaction<R> {
    return Transaction.Fail(throwable)
}