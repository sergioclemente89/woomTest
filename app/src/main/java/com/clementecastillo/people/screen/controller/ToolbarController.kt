package com.clementecastillo.people.screen.controller

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ToolbarController {
    fun setScreenTitle(@StringRes titleRes: Int)
    fun hideBackButton()
    fun showBackButton()
    fun setToolbarColor(@ColorRes color: Int)
}