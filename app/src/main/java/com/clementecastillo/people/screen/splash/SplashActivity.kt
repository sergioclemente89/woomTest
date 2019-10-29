package com.clementecastillo.people.screen.splash

import android.animation.Animator
import android.os.Bundle
import com.clementecastillo.people.R
import com.clementecastillo.people.screen.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.splash_layout.*
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {

    @Inject
    lateinit var presenter: SplashPresenter

    private val onAnimationFinishSubject = PublishSubject.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)

        screenComponent.inject(this)

        splash_animation_view.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                onAnimationFinishSubject.onNext(Unit)
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}
        })

        init(presenter, this)
    }

    override fun onAnimationFinish(): Observable<Unit> {
        return onAnimationFinishSubject
    }
}