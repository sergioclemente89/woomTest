package com.clementecastillo.people.screen.persondetail

import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.presenter.PresenterView

interface PersonDetailView : PresenterView {

    fun getPersonUuid(): String
    fun bindPerson(person: Person)
}