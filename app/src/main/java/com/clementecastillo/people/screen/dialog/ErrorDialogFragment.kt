package com.clementecastillo.people.screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clementecastillo.people.R
import com.clementecastillo.people.screen.base.BaseDialogFragment
import kotlinx.android.synthetic.main.error_dialog_layout.view.*

class ErrorDialogFragment : BaseDialogFragment() {
    companion object {
        private const val MESSAGE_KEY_RES = "com.clementecastillo.people.screen.dialog.ErrorDialogFragment.MESSAGE_KEY_RES"

        fun default(): ErrorDialogFragment = create(R.string.default_error_message)

        fun create(messageRes: Int): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            fragment.arguments = Bundle().apply {
                putInt(MESSAGE_KEY_RES, messageRes)
            }
            return fragment
        }
    }

    init {
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.error_dialog_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when {
            arguments?.getInt(MESSAGE_KEY_RES) != null -> {
                view.alert_message.setText(arguments?.getInt(MESSAGE_KEY_RES)!!)
            }
            else -> throw IllegalArgumentException()
        }
        view.alert_accept_button.setOnClickListener {
            dismiss()
        }
    }

}