package com.clementecastillo.people

import android.app.Application
import com.clementecastillo.people.injection.component.AppComponent
import com.clementecastillo.people.injection.component.DaggerAppComponent
import com.clementecastillo.people.injection.controller.AppController
import com.clementecastillo.people.injection.module.AppModule

class PeopleApp : Application(), AppController {

    companion object {
        lateinit var appController: AppController
    }

    override val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appController = this
    }
}