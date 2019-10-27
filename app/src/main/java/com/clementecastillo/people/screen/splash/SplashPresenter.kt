package com.clementecastillo.people.screen.splash

import com.clementecastillo.people.presenter.Presenter
import com.clementecastillo.people.screen.controller.RouterController
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val routerController: RouterController
) : Presenter<SplashView>() {

    override fun init(view: SplashView) {
        view.onAnimationFinish().subscribe {
            routerController.routeToPeopleList()
        }.addTo(disposables)
    }
}