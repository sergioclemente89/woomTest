package com.clementecastillo.people.injection.component

import com.clementecastillo.people.injection.ScreenScope
import com.clementecastillo.people.injection.module.ScreenModule
import com.clementecastillo.people.screen.peoplelist.PeopleListActivity
import com.clementecastillo.people.screen.persondetail.PersonDetailActivity
import com.clementecastillo.people.screen.splash.SplashActivity
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class], modules = [ScreenModule::class])
interface ScreenComponent {
    fun inject(splashActivity: SplashActivity)
    fun inject(peopleListActivity: PeopleListActivity)
    fun inject(personDetailActivity: PersonDetailActivity)

}