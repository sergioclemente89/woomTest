package com.clementecastillo.people.usecase.common

interface UseCase<out T> {

    fun bind(): T
}