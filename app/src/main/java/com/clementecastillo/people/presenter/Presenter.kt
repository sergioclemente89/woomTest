package com.clementecastillo.people.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class Presenter<V : PresenterView> {

    var disposables: CompositeDisposable = CompositeDisposable()

    fun initWith(presenterView: V) {
        clear()
        disposables = CompositeDisposable()
        init(presenterView)
    }

    fun clear() {
        disposables.dispose()
    }

    protected abstract fun init(view: V)
}