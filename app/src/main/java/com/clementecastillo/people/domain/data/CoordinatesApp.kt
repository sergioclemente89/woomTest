package com.clementecastillo.people.domain.data

import com.clementecastillo.core.domain.data.Coordinates

data class CoordinatesApp(override val latitude: String, override val longitude: String) : Coordinates