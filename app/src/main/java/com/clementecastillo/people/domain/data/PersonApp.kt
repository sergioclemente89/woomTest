package com.clementecastillo.people.domain.data

import com.clementecastillo.core.domain.data.Person
import com.google.gson.annotations.SerializedName

data class PersonApp(
    @SerializedName("login")
    override val personId: PersonIdApp,
    override val gender: Person.GENDER,
    @SerializedName("name")
    override val personName: PersonNameApp,
    @SerializedName("picture")
    override val personPicture: PersonPictureApp,
    @SerializedName("location")
    override val personLocation: PersonLocationApp,
    override val email: String,
    override val phone: String,
    @SerializedName("registered")
    override val registrationDate: RegistrationDateApp
) : Person