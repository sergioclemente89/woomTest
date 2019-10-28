package com.clementecastillo.people.screen.persondetail

import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.R
import com.clementecastillo.people.client.ImageLoader
import com.clementecastillo.people.screen.base.BaseActivity
import com.clementecastillo.people.screen.controller.ToolbarController
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
    }

    override fun getPersonUuid(): String {
        return personUuid
    }
}
