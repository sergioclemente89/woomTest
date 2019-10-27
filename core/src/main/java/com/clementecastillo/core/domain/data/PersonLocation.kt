package com.clementecastillo.core.domain.data

interface PersonLocation {
    val city: String
    val coordinates: Coordinates
    val country: String
    val postcode: Int
    val state: String
    val street: Street
}