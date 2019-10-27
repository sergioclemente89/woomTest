package com.clementecastillo.people.screen.peoplelist

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.R
import com.clementecastillo.people.screen.base.BaseActivity
import com.clementecastillo.people.screen.peoplelist.adapter.PeopleAdapter
import kotlinx.android.synthetic.main.people_list_layout.*
import javax.inject.Inject

class PeopleListActivity : BaseActivity(), PeopleListView {

    @Inject
    lateinit var presenter: PeopleListPresenter

    private val peopleAdapter = PeopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_list_layout)

        screenComponent.inject(this)

        configureReyclerView()

        init(presenter, this)
    }

    private fun configureReyclerView() {
        people_recyclerview.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = peopleAdapter
        }
    }

    override fun addPeople(peopleList: List<Person>) {
        peopleAdapter.addData(peopleList)
    }
}