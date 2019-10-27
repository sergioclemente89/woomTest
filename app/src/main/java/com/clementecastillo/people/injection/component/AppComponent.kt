package com.clementecastillo.people.injection.component

import android.content.res.Resources
import com.clementecastillo.core.domain.provider.PeopleProvider
import com.clementecastillo.people.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun provideResources(): Resources
    fun providePeopleProvider(): PeopleProvider
}