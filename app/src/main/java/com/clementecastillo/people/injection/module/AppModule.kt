package com.clementecastillo.people.injection.module

import android.content.Context
import android.content.res.Resources
import com.clementecastillo.people.PeopleApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [ClientModule::class]
)
class AppModule(private val app: PeopleApp) {

    @Provides
    @Singleton
    fun resources(): Resources = app.resources

    @Provides
    @Singleton
    fun context(): Context = app.applicationContext

}