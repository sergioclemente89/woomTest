package com.clementecastillo.people.domain.data

import com.clementecastillo.core.domain.data.Street

data class StreetApp(override val streetName: String, override val number: Int) : Street