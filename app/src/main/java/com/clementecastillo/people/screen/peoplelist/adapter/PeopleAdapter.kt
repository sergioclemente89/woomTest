package com.clementecastillo.people.screen.peoplelist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.R
import kotlinx.android.synthetic.main.person_itemview.view.*

class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.PersonHolder>() {

    private val peopleList: MutableList<Person> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        return PersonHolder(LayoutInflater.from(parent.context).inflate(R.layout.person_itemview, parent, false))
    }

    override fun getItemCount(): Int = peopleList.size

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        holder.bindPerson(peopleList[position])
    }

    fun addData(newPeopleList: List<Person>) {
        peopleList.addAll(newPeopleList)
        notifyDataSetChanged()
    }

    inner class PersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindPerson(person: Person) {
            itemView.run {
                person_name.text = "${person.personName.title} ${person.personName.first} ${person.personName.last}"
            }
        }
    }
}