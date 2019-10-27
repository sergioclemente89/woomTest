package com.clementecastillo.people.screen.base

import android.annotation.SuppressLint
import android.content.Intent
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.clementecastillo.people.R
import com.clementecastillo.people.extension.fadeIn
import com.clementecastillo.people.extension.fadeOut
import com.clementecastillo.people.extension.hideKeyboard
import com.clementecastillo.people.injection.component.DaggerScreenComponent
import com.clementecastillo.people.injection.component.ScreenComponent
import com.clementecastillo.people.injection.controller.AppController
import com.clementecastillo.people.injection.controller.ScreenController
import com.clementecastillo.people.injection.module.ScreenModule
import com.clementecastillo.people.presenter.Presenter
import com.clementecastillo.people.presenter.PresenterView
import com.clementecastillo.people.screen.controller.LoadingController
import com.clementecastillo.people.screen.controller.RouterController
import com.clementecastillo.people.screen.controller.ToolbarController
import com.clementecastillo.people.screen.peoplelist.PeopleListActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.toolbar_view.*

@SuppressLint("RegistrationDate")
abstract class BaseActivity : AppCompatActivity(), ScreenController, RouterController, LoadingController, ToolbarController {

    private var presenter: Presenter<*>? = null
    private var disposables = CompositeDisposable()

    fun <T : PresenterView> init(presenter: Presenter<T>, view: T) {
        this.presenter = presenter
        presenter.initWith(view)
    }

    override fun onDestroy() {
        disposables.clear()
        presenter?.clear()
        super.onDestroy()
    }

    override val screenComponent: ScreenComponent by lazy {
        DaggerScreenComponent.builder()
            .appComponent((applicationContext as AppController).appComponent)
            .screenModule(ScreenModule(this))
            .build()
    }

    private fun routeTo(activity: Class<out BaseActivity>, finish: Boolean, clearStack: Boolean) {
        hideKeyboard(this)
        val intent = Intent(this, activity)
        if (clearStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
        if (finish) {
            finish()
        }
    }

    override fun routeToPeopleList() {
        routeTo(PeopleListActivity::class.java, true, true)
    }

    override fun close() {
        finish()
    }

    override fun showLoading() {
        loading_view?.fadeIn()
    }

    override fun hideLoading() {
        loading_view?.fadeOut()
    }

    override fun hideBackButton() {
        toolbar_view.navigationIcon = null
    }

    override fun showBackButton() {
        toolbar_view.run {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun setScreenTitle(@StringRes titleRes: Int) {
        toolbar_title.setText(titleRes)
    }
}