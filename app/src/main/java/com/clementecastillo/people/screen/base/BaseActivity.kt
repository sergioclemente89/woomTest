package com.clementecastillo.people.screen.base

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.clementecastillo.people.R
import com.clementecastillo.people.extension.fadeIn
import com.clementecastillo.people.extension.fadeOut
import com.clementecastillo.people.extension.hideKeyboard
import com.clementecastillo.people.extension.tagName
import com.clementecastillo.people.injection.component.DaggerScreenComponent
import com.clementecastillo.people.injection.component.ScreenComponent
import com.clementecastillo.people.injection.controller.AppController
import com.clementecastillo.people.injection.controller.ScreenController
import com.clementecastillo.people.injection.module.ScreenModule
import com.clementecastillo.people.presenter.Presenter
import com.clementecastillo.people.presenter.PresenterView
import com.clementecastillo.people.screen.controller.LoadingController
import com.clementecastillo.people.screen.controller.RouterController
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.loading_layout.*

@SuppressLint("RegistrationDate")
abstract class BaseActivity : AppCompatActivity(), ScreenController, RouterController, LoadingController {

    companion object {
        private const val BASE_CONTAINER_ID = R.id.frame_container

    }

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

    fun currentFragment(): BaseFragment? {
        return supportFragmentManager.findFragmentById(BASE_CONTAINER_ID) as BaseFragment?
    }

    override fun routeTo(fragment: BaseFragment, backStack: Boolean) {
        hideKeyboard(this)
        val currentFragment = currentFragment()
        if (currentFragment == null || currentFragment.javaClass != fragment.javaClass) {
            val transaction = supportFragmentManager.beginTransaction()
            if (backStack) {
                transaction.addToBackStack(fragment.tagName())
            }
            transaction.replace(BASE_CONTAINER_ID, fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    override fun routeTo(activity: Class<out BaseActivity>, finish: Boolean, clearStack: Boolean) {
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

    override fun onBackPressed() {
        val fragment: BaseFragment? = currentFragment()
        if (fragment == null || !fragment.onBackPressed()) {
            super.onBackPressed()
        }
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

}