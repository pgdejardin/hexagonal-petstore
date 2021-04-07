package org.example.infrastructure.pets.api

import io.kotest.core.spec.style.FunSpec
import org.example.domain.pets.NewPets
import org.example.domain.pets.PetEnvironment
import org.example.infrastructure.pets.repository.FakePetRepository
import org.example.infrastructure.pets.repository.FakeSqsPetRepository
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.kotest.shouldHaveBody
import org.http4k.kotest.shouldHaveStatus

class PetRestEndpointsTest : FunSpec({
  val petEndpoints = PetRestEndpoints(NewPets(PetEnvironment(FakePetRepository, FakeSqsPetRepository)))
  val server = ServerFilters.CatchAll().then(petEndpoints.endpoints)

  test("GET /pets should return a pet") {
    val response = server(Request(GET, "/"))

    response shouldHaveStatus OK
    response shouldHaveBody """{"id":"123-abc-123","name":"name","category":"","bio":"","status":{},"tags":[],"photoUrls":[]}"""
  }

  test("POST /pets should create a pet") {
    val petToCreate = """{"name": "Hachiko", "category": "Dog", "bio": "Wan! Wan!"}"""
    val response = server(Request(POST, "/").body(petToCreate))

    response shouldHaveStatus CREATED
    response shouldHaveBody """{"id":"fa18a1af-1f84-445c-8f79-3f264b76d84f","name":"Hachiko","category":"Dog","bio":"Wan! Wan!","status":{},"tags":[],"photoUrls":[]}"""
  }
})
