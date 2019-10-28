package com.clementecastillo.people.screen.controller

interface RouterController {

    fun close()

    fun routeToPeopleList()

    fun routeToPersonDetail(personUuid: String)
}