package com.clementecastillo.people.domain.data

import com.clementecastillo.core.domain.data.PersonPicture

data class PersonPictureApp(override val large: String, override val medium: String, override val thumbnail: String) : PersonPicture