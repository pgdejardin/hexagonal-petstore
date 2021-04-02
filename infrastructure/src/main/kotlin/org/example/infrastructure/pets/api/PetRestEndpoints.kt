package org.example.infrastructure.pets.api

import org.example.domain.pets.NewPets
import org.example.domain.pets.PetStatus
import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.routes

class PetRestEndpoints(private val newPets: NewPets) {
  private val jsonPetMarshaller = Body.auto<JsonPet>().toLens()
  private val createPetMarshaller = Body.auto<CreatePet>().toLens()

  val endpoints = routes(
    "/" bind GET to {
      Response(OK).with(jsonPetMarshaller of JsonPet("123-abc-123", "name", "", "", PetStatus.Available))
    },
    "/" bind POST to { req ->
      val received = createPetMarshaller(req)
      newPets.create(received.toPet()).fold({ Response(BAD_REQUEST) }, { Response(CREATED).with(jsonPetMarshaller of it.toJsonPet()) })
    }
  )
}


