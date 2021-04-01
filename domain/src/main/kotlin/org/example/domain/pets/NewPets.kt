package org.example.domain.pets

import arrow.core.Either
import arrow.core.flatMap
import org.example.domain.PetErrors
import org.example.domain.PetErrors.CannotGenerateId
import org.example.domain.pets.adapter.PetRepository
import java.util.*

class NewPets(private val env: PetEnvironment) {
  fun create(pet: Pet): Either<PetErrors, Pet> = with(env) {
    Either.catch { UUID.randomUUID() }
      .mapLeft { CannotGenerateId }
      .flatMap { petRepository.create(pet.copy(id = it)) }
  }
}
