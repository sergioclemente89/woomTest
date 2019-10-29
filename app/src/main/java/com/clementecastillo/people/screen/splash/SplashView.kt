package com.clementecastillo.people.screen.splash

import com.clementecastillo.people.presenter.PresenterView
import io.reactivex.Observable

interface SplashView : PresenterView {
    fun onAnimationFinish(): Observable<Unit>
}