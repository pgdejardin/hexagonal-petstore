package org.example.infrastructure.pets.repository

import arrow.core.Either
import arrow.core.Either.Right
import org.example.domain.PetErrors
import org.example.domain.pets.Pet
import org.example.domain.pets.adapter.PetProducer
import org.example.domain.pets.adapter.PetRepository
import java.util.*

object FakePetRepository: PetRepository {
  override fun create(pet: Pet): Either<PetErrors, Pet> =
    Right(Pet("Hachiko", "Dog", "Wan! Wan!", id = UUID.fromString("fa18a1af-1f84-445c-8f79-3f264b76d84f")))
}

object FakeSqsPetRepository: PetProducer {
  override fun newPetAdded(pet: Pet): Either<PetErrors.PetProducerError, Pet> = Right(pet)
}
