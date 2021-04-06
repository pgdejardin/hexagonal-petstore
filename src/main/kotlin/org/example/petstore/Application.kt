package org.example.petstore

import org.example.domain.pets.NewPets
import org.example.domain.pets.PetEnvironment
import org.example.infrastructure.pets.api.PetRestEndpoints
import org.example.infrastructure.pets.repository.InMemoryPetRepository
import org.http4k.core.Method
import org.http4k.core.then
import org.http4k.filter.AllowAllOriginPolicy
import org.http4k.filter.CorsPolicy
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import com.typesafe.config.ConfigFactory
import io.github.config4k.*
import org.example.infrastructure.pets.repository.SqsPetRepository
import org.example.petstore.config.ServerConfig

fun main() {
  // Environments
  val petEnv = PetEnvironment(InMemoryPetRepository)

  // AWS Services
  SqsPetRepository.createNewPetQueue()

  // Domain Impl
  val newPets = NewPets(petEnv)

  // APIs
  val petApi = PetRestEndpoints(newPets)

  val configLoader = ConfigFactory.load()
  val serverConfig: ServerConfig = configLoader.extract("http")

  val app = DebuggingFilters.PrintRequestAndResponse()
    .then(ServerFilters.Cors(CorsPolicy(AllowAllOriginPolicy, listOf("Authorization"), Method.values().toList(), true)))
    .then(routes("/pets" bind petApi.endpoints))

  app.asServer(SunHttp(serverConfig.port)).start()
}
