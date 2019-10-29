package com.clementecastillo.core.domain.repository.people

import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.core.domain.repository.common.CacheRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleCacheRepository @Inject constructor() : CacheRepository<List<Person>>()