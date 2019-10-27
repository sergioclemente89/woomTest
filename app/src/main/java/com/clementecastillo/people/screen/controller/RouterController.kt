package com.clementecastillo.people.screen.controller

import com.clementecastillo.people.screen.base.BaseActivity
import com.clementecastillo.people.screen.base.BaseFragment

interface RouterController {

    fun routeTo(fragment: BaseFragment, backStack: Boolean = false)

    fun routeTo(
        activity: Class<out BaseActivity>,
        finish: Boolean = false,
        clearStack: Boolean = false
    )

    fun close()

}