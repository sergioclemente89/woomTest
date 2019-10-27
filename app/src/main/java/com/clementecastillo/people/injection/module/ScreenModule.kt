package com.clementecastillo.people.injection.module

import com.clementecastillo.people.injection.ScreenScope
import com.clementecastillo.people.screen.base.BaseActivity
import com.clementecastillo.people.screen.controller.LoadingController
import com.clementecastillo.people.screen.controller.RouterController
import dagger.Module
import dagger.Provides

@Module
class ScreenModule(private val activity: BaseActivity) {

    @ScreenScope
    @Provides
    fun routerController(): RouterController = activity

    @ScreenScope
    @Provides
    fun loadingController(): LoadingController = activity

//    @ScreenScope
//    @Provides
//    fun toolbarController(): ToolbarController = activity
}