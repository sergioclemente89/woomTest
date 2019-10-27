package com.clementecastillo.people.screen.base

import androidx.fragment.app.Fragment
import com.clementecastillo.people.injection.component.ScreenComponent
import com.clementecastillo.people.injection.controller.ScreenController
import com.clementecastillo.people.presenter.Presenter
import com.clementecastillo.people.presenter.PresenterView
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    var disposables = CompositeDisposable()
    private var presenter: Presenter<*>? = null

    fun <T : PresenterView> init(presenter: Presenter<T>, view: T) {
        this.presenter = presenter
        presenter.initWith(view)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
        presenter?.clear()
    }

    open fun onBackPressed(): Boolean = false

    fun screen(): ScreenComponent {
        return (activity as ScreenController).screenComponent
    }
}