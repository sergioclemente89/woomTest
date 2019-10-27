package com.clementecastillo.people.screen.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.clementecastillo.people.R
import com.clementecastillo.people.injection.component.ScreenComponent
import com.clementecastillo.people.extension.tagName
import com.clementecastillo.people.extension.toPx
import com.clementecastillo.people.injection.controller.ScreenController
import com.clementecastillo.people.presenter.Presenter
import com.clementecastillo.people.presenter.PresenterView
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

open class BaseDialogFragment : DialogFragment() {

    private var disposables = CompositeDisposable()

    private var presenter: Presenter<*>? = null
    private val eventEmitter: PublishSubject<DialogStateEvent> = PublishSubject.create()

    fun <T : PresenterView> init(presenter: Presenter<T>, view: T) {
        this.presenter = presenter
        presenter.initWith(view)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
        presenter?.clear()
    }

    fun screen(): ScreenComponent = (activity as ScreenController).screenComponent

    open var transparent: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.run {
            if (transparent) {
                val back = ColorDrawable(Color.TRANSPARENT)
                val inset = InsetDrawable(back, 0, 24.toPx(), 0, 24.toPx())
                setBackgroundDrawable(inset)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        val lp = window?.attributes

        if (transparent) {
            val padding = resources.getDimensionPixelSize(R.dimen.size_m)
            val width = (resources.displayMetrics.widthPixels - padding * 2f).toInt()
            lp?.width = width
        } else {
            lp?.width = WindowManager.LayoutParams.MATCH_PARENT
        }

        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = lp
    }

    fun show(fragmentManager: FragmentManager): BaseDialogFragment {
        val tag = tagName()
        val currentFragment = fragmentManager.findFragmentByTag(tag) as BaseDialogFragment?
        val dialog = currentFragment?.dialog
        return if (currentFragment == null || dialog == null || !dialog.isShowing) {
            try {
                show(fragmentManager, tag)
            } catch (e: Exception) { /**/
                e.printStackTrace()
            }
            this
        } else {
            currentFragment
        }
    }

    fun clear(fragmentManager: FragmentManager) {
        val tag = tagName()
        val currentFragment = fragmentManager.findFragmentByTag(tag) as BaseDialogFragment?
        currentFragment?.dismiss()
    }

    fun events(): Observable<DialogStateEvent> = eventEmitter

    fun onAttached(): Completable {
        return Completable.fromSingle(eventEmitter.filter {
            it == DialogStateEvent.ATTACHED
        }.firstElement().toSingle())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        eventEmitter.onNext(DialogStateEvent.ATTACHED)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        eventEmitter.onNext(DialogStateEvent.DETACHED)
    }

    fun emitEvent(event: DialogStateEvent) {
        eventEmitter.onNext(event)
    }

    enum class DialogStateEvent {
        CONFIRM, CANCEL, ATTACHED, DETACHED, ERROR
    }
}