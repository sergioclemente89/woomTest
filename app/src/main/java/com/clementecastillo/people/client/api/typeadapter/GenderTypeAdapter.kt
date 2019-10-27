package com.clementecastillo.people.client.api.typeadapter

import com.clementecastillo.core.domain.data.Person
import com.google.gson.*
import java.lang.reflect.Type

class GenderTypeAdapter : JsonDeserializer<Person.GENDER>, JsonSerializer<Person.GENDER> {

    override fun serialize(src: Person.GENDER, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return context.serialize(src.name.toLowerCase())
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Person.GENDER {
        return Person.GENDER.valueOf(json.asString.toUpperCase())
    }
}