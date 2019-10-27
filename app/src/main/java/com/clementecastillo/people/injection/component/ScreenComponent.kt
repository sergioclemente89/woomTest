package com.clementecastillo.people.injection.component

import com.clementecastillo.people.injection.ScreenScope
import com.clementecastillo.people.injection.module.ScreenModule
import com.clementecastillo.people.screen.splash.SplashActivity
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class], modules = [ScreenModule::class])
interface ScreenComponent {
    fun inject(splashActivity: SplashActivity)

}