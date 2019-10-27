package com.clementecastillo.people.client.api.data

import com.clementecastillo.people.domain.data.PersonApp
import com.google.gson.annotations.SerializedName

data class GetPeopleResponse(
    @SerializedName("results")
    val peopleList: List<PersonApp>
)