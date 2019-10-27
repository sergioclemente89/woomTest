package com.clementecastillo.people.screen.peoplelist

import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.presenter.PresenterView
import io.reactivex.Observable

interface PeopleListView : PresenterView {

    fun addPeople(peopleList: List<Person>)
    fun onRequestNextPage(): Observable<Int>
}