package com.clementecastillo.people.usecase.common

interface UseCaseWithParams<out T, in P> {

    fun bind(params: P): T
}