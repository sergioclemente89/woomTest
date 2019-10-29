package com.clementecastillo.core.domain.data


interface Person {
    val personId: PersonId
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

    fun getFullName(): String {
        return "${personName.title} ${personName.first} ${personName.last}"
    }

    fun getAddress(): String {
        return "${personLocation.street.streetName} ${personLocation.street.number}, ${personLocation.city} (${personLocation.state})"
    }
}