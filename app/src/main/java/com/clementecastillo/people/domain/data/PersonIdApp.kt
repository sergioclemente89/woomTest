package com.clementecastillo.people.domain.data

import com.clementecastillo.core.domain.data.PersonId

data class PersonIdApp(
    override val uuid: String
) : PersonId