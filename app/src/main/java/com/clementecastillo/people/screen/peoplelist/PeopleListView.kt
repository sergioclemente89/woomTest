package com.clementecastillo.people.screen.peoplelist

import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.presenter.PresenterView

interface PeopleListView : PresenterView {

    fun addPeople(peopleList: List<Person>)
}