package com.clementecastillo.people.domain.data

import com.clementecastillo.core.domain.data.PersonName

data class PersonNameApp(override val first: String, override val last: String, override val title: String) : PersonName