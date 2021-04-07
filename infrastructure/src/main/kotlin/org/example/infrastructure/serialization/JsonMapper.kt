package org.example.infrastructure.serialization

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.example.domain.TechnicalFailures.SerializationFailure

object JsonMapper {
  val mapper: ObjectMapper = jacksonObjectMapper()
}

fun<A> ObjectMapper.unmarshall(a: A): Either<SerializationFailure, String> = Either.catch { this.writeValueAsString(a) }.mapLeft { SerializationFailure }

