package com.clementecastillo.people.screen.base

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.clementecastillo.people.screen.dialog.ErrorDialogFragment
import com.clementecastillo.people.screen.peoplelist.PeopleListActivity
import com.clementecastillo.people.screen.persondetail.PersonDetailActivity
import io.reactivex.Observable
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

    private fun routeTo(activity: Class<out BaseActivity>, finish: Boolean, clearStack: Boolean, extras: Bundle? = null) {
        hideKeyboard(this)
        val intent = Intent(this, activity)
        if (clearStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        extras?.let {
            intent.putExtras(it)
        }
        startActivity(intent)
        if (finish) {
            finish()
        }
    }

    override fun routeToPeopleList() {
        routeTo(PeopleListActivity::class.java, true, true)
    }

    override fun routeToPersonDetail(personUuid: String) {
        val extras = Bundle().apply {
            putString(PersonDetailActivity.PERSON_DETAIL_UUID, personUuid)
        }
        routeTo(PersonDetailActivity::class.java, false, false, extras = extras)
    }

    override fun sendEmailTo(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        startActivity(intent)
    }

    override fun openCaller(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
    }

    override fun openNavigationTo(latitude: String, longitude: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:0,0?q=$latitude,$longitude")
        }
        startActivity(intent)
    }

    override fun showErrorDialog(messageRes: Int?): Observable<BaseDialogFragment.DialogStateEvent> {
        val dialog = if (messageRes == null) {
            ErrorDialogFragment.default()
        } else {
            ErrorDialogFragment.create(messageRes)
        }
        return dialog.show(supportFragmentManager).events()
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

    override fun setToolbarColor(color: Int) {
        toolbar_view.setBackgroundColor(ContextCompat.getColor(this, color))
    }
}