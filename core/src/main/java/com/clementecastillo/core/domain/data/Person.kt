package com.clementecastillo.core.domain.data


interface Person {
    val gender: GENDER
    val personName: PersonName
    val personPicture: PersonPicture
    val personLocation: PersonLocation
    val email: String
    val phone: String
    val registrationDate: RegistrationDate

    enum class GENDER {
        MALE, FEMALE
    }
}