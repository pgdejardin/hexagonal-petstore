package org.example.domain.pets

import arrow.core.Either
import arrow.core.flatMap
import org.example.domain.PetErrors
import org.example.domain.PetErrors.CannotGenerateId
import java.util.*

class NewPets(private val env: PetEnvironment) {
  fun create(pet: Pet): Either<PetErrors, Pet> = with(env) {
    val id = Either.catch { UUID.randomUUID() }.mapLeft { CannotGenerateId }
    val petCreated = id.flatMap { petRepository.create(pet.copy(id = it)) }
    petCreated.flatMap { petProducer.newPetAdded(it) }
  }
}
