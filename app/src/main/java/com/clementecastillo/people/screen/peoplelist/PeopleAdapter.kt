package com.clementecastillo.people.screen.peoplelist

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.core.domain.data.Person.GENDER.FEMALE
import com.clementecastillo.core.domain.data.Person.GENDER.MALE
import com.clementecastillo.people.R
import com.clementecastillo.people.client.ImageLoader
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.person_female_itemview.view.*

class PeopleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val MALE_VIEWTYPE = 0
        private const val FEMALE_VIEWTYPE = 1
        private const val PAGER_VIEWTYPE = 2
    }

    private val peopleList: MutableList<Person> = mutableListOf()
    private val nextPageSubject = PublishSubject.create<Int>()
    private val onPersonClickSubject = PublishSubject.create<String>()
    private var canLoadMore = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MALE_VIEWTYPE -> PersonHolder(LayoutInflater.from(parent.context).inflate(R.layout.person_male_itemview, parent, false))
            FEMALE_VIEWTYPE -> PersonHolder(LayoutInflater.from(parent.context).inflate(R.layout.person_female_itemview, parent, false))
            PAGER_VIEWTYPE -> PagerHolder(LayoutInflater.from(parent.context).inflate(R.layout.pager_item_view, parent, false))
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        fun personViewType(gender: Person.GENDER): Int {
            return when (gender) {
                MALE -> MALE_VIEWTYPE
                FEMALE -> FEMALE_VIEWTYPE
            }
        }
        return when (position) {
            itemCount - 1 -> PAGER_VIEWTYPE
            else -> personViewType(peopleList[position].gender)
        }
    }

    override fun getItemCount(): Int {
        return peopleList.size + if (canLoadMore) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PersonHolder -> holder.bindPerson(peopleList[position])
            is PagerHolder -> holder.bind()
        }
    }

    fun addData(newPeopleList: List<Person>) {
        canLoadMore = newPeopleList.isNotEmpty()
        val currentSize = itemCount
        peopleList.addAll(newPeopleList)
        if (currentSize == 1) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(currentSize, peopleList.size)
        }
    }

    fun onNextPage(): Observable<Int> = nextPageSubject
    fun onPersonClick(): Observable<String> = onPersonClickSubject

    inner class PersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindPerson(person: Person) {
            itemView.run {
                person_name.text = "${person.personName.title} ${person.personName.first} ${person.personName.last}"
                person_email.text = person.email
                person_phone.text = person.phone

                ImageLoader.load(Uri.parse(person.personPicture.medium), person_photo, person_photo.width, person_photo.height, true, person_photo.drawable)
                person_layout.setOnClickListener {
                    onPersonClickSubject.onNext(person.personId.uuid)
                }
            }
        }
    }

    inner class PagerHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            nextPageSubject.onNext(peopleList.size)
        }
    }
}