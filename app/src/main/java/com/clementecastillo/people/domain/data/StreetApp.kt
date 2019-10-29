package com.clementecastillo.people.domain.data

import com.clementecastillo.core.domain.data.Street
import com.google.gson.annotations.SerializedName

data class StreetApp(
    @SerializedName("name")
    override val streetName: String,
    override val number: Int
) : Street