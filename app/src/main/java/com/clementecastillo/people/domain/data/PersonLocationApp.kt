package com.clementecastillo.people.domain.data

import com.clementecastillo.core.domain.data.PersonLocation

data class PersonLocationApp(
    override val city: String,
    override val coordinates: CoordinatesApp,
    override val country: String,
    override val postcode: String,
    override val state: String,
    override val street: StreetApp
) : PersonLocation