package com.clementecastillo.people.screen.controller

import com.clementecastillo.people.screen.base.BaseDialogFragment
import io.reactivex.Observable

interface RouterController {

    fun close()

    fun routeToPeopleList()

    fun routeToPersonDetail(personUuid: String)

    fun sendEmailTo(email: String)

    fun openCaller(phone: String)

    fun openNavigationTo(latitude: String, longitude: String)

    fun showErrorDialog(messageRes: Int? = null): Observable<BaseDialogFragment.DialogStateEvent>
}