package com.clementecastillo.people.screen.persondetail

import com.clementecastillo.core.domain.data.Coordinates
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.presenter.PresenterView
import io.reactivex.Observable

interface PersonDetailView : PresenterView {

    fun getPersonUuid(): String
    fun bindPerson(person: Person)
    fun onEmailButtonClick(): Observable<String>
    fun onPhoneButtonClick(): Observable<String>
    fun onLocationButtonClick(): Observable<Coordinates>
}