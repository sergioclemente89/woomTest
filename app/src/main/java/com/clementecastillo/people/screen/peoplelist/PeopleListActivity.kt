package com.clementecastillo.people.screen.peoplelist

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.R
import com.clementecastillo.people.screen.base.BaseActivity
import com.clementecastillo.people.screen.controller.ToolbarController
import com.clementecastillo.people.screen.peoplelist.adapter.PeopleAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.people_list_layout.*
import javax.inject.Inject

class PeopleListActivity : BaseActivity(), PeopleListView {

    @Inject
    lateinit var presenter: PeopleListPresenter
    @Inject
    lateinit var toolbarController: ToolbarController

    private val peopleAdapter = PeopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_list_layout)
        screenComponent.inject(this)

        setTitle()
        configureReyclerView()

        init(presenter, this)
    }

    private fun setTitle() {
        toolbarController.run {
            setScreenTitle(R.string.people_list)
            hideBackButton()
        }
    }

    private fun configureReyclerView() {
        people_recyclerview.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = peopleAdapter
        }
    }

    override fun addPeople(peopleList: List<Person>) {
        peopleAdapter.addData(peopleList)
    }

    override fun onRequestNextPage(): Observable<Int> {
        return peopleAdapter.onNextPage()
    }
}