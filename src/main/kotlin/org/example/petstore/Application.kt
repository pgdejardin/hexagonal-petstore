package org.example.petstore

import org.example.domain.pets.NewPets
import org.example.domain.pets.PetEnvironment
import org.example.infrastructure.pets.api.PetRestEndpoints
import org.example.infrastructure.pets.repository.InMemoryPetRepository
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
  // Environments
  val petEnv = PetEnvironment(InMemoryPetRepository)

  // Domain Impl
  val newPets = NewPets(petEnv)

  // APIs
  val petApi = PetRestEndpoints(newPets)

  val app = DebuggingFilters.PrintRequestAndResponse().then(
    routes(
      "/pets" bind petApi.endpoints
    )
  )

  app.asServer(SunHttp(9000)).start()
}
