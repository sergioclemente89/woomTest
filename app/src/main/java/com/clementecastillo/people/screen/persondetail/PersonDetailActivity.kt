package com.clementecastillo.people.screen.persondetail

import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.clementecastillo.core.domain.data.Coordinates
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.R
import com.clementecastillo.people.client.ImageLoader
import com.clementecastillo.people.extension.getFormattedDate
import com.clementecastillo.people.screen.base.BaseActivity
import com.clementecastillo.people.screen.controller.ToolbarController
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.content_person_detail.*
import kotlinx.android.synthetic.main.person_detail_layout.*
import javax.inject.Inject

class PersonDetailActivity : BaseActivity(), PersonDetailView {

    companion object {
        const val PERSON_DETAIL_UUID = "com.clementecastillo.people.screen.persondetail.PERSON_DETAIL_UUID"
    }

    @Inject
    lateinit var presenter: PersonDetailPresenter
    @Inject
    lateinit var toolbarController: ToolbarController

    private var personUuid: String = ""
    private val onEmailButtonClickSubject = PublishSubject.create<String>()
    private val onPhoneButtonClickSubject = PublishSubject.create<String>()
    private val onLocationButtonClickSubject = PublishSubject.create<Coordinates>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_detail_layout)
        screenComponent.inject(this)

        configureToolbar()
        personUuid = intent?.getStringExtra(PERSON_DETAIL_UUID).toString()

        init(presenter, this)
    }

    private fun configureToolbar() {
        toolbarController.run {
            showBackButton()
        }
    }

    override fun bindPerson(person: Person) {
        person_detail_photo.run {
            post {
                ImageLoader.load(
                    Uri.parse(person.personPicture.large),
                    this,
                    width,
                    height,
                    errorDrawable = ContextCompat.getDrawable(context, R.drawable.ic_avatar_male)
                )
            }

        }
        person_detail_gender_textview.run {
            when (person.gender) {
                Person.GENDER.MALE -> {
                    setText(R.string.male)
                    setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_gender_male), null, null, null)
                }
                Person.GENDER.FEMALE -> {
                    setText(R.string.female)
                    setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_gender_female), null, null, null)
                }
            }
        }

        person_detail_name_textview.text = person.getFullName()
        person_detail_location_textview.text = person.getAddress()
        person_detail_location_cardview.setOnClickListener {
            onLocationButtonClickSubject.onNext(person.personLocation.coordinates)
        }
        person_detail_registration_textview.text = getString(R.string.registration_date_x, person.registrationDate.date.getFormattedDate())
        person_detail_email_textview.text = person.email
        person_detail_email_cardview.setOnClickListener {
            onEmailButtonClickSubject.onNext(person.email)
        }
        person_detail_phone_button.setOnClickListener {
            onPhoneButtonClickSubject.onNext(person.phone)
        }
    }

    override fun getPersonUuid(): String {
        return personUuid
    }

    override fun onEmailButtonClick(): Observable<String> {
        return onEmailButtonClickSubject
    }

    override fun onPhoneButtonClick(): Observable<String> {
        return onPhoneButtonClickSubject
    }

    override fun onLocationButtonClick(): Observable<Coordinates> {
        return onLocationButtonClickSubject
    }
}
