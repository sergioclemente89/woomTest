package com.clementecastillo.people.extension

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.throttleDefault(): Observable<T> {
    return this.throttleFirst(500, TimeUnit.MILLISECONDS)
}

fun <T> Single<T>.mainThread(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.mainThread(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun Completable.mainThread(): Completable {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.mainThread(): Maybe<T> {
    return observeOn(AndroidSchedulers.mainThread())
}