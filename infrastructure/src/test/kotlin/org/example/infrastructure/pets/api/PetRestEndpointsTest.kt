package org.example.infrastructure.pets.api

import io.kotest.core.spec.style.FunSpec
import org.example.domain.pets.NewPets
import org.example.domain.pets.PetEnvironment
import org.example.domain.pets.PetStatus
import org.example.infrastructure.pets.repository.InMemoryPetRepository
import org.http4k.core.*
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson.auto
import org.http4k.kotest.shouldHaveBody
import org.http4k.kotest.shouldHaveStatus
import org.http4k.lens.string

class PetRestEndpointsTest : FunSpec({
  val petEndpoints = PetRestEndpoints(NewPets(PetEnvironment(InMemoryPetRepository)))
  val server = ServerFilters.CatchAll().then(petEndpoints.endpoints)

  test("GET /pet should return a pet") {
    val response = server(Request(Method.GET, "/"))

    response shouldHaveStatus Status.OK
    response shouldHaveBody """{"id":"123-abc-123","name":"name","category":"","bio":"","status":{},"tags":[],"photoUrls":[]}"""
  }
})
