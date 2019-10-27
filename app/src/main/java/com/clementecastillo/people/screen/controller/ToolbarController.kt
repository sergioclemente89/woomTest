package com.clementecastillo.people.screen.controller

import androidx.annotation.StringRes

interface ToolbarController {
    fun setScreenTitle(@StringRes titleRes: Int)
    fun hideBackButton()
    fun showBackButton()
}