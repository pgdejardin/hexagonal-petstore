package org.example.domain.pets.adapter

import arrow.core.Either
import org.example.domain.PetErrors
import org.example.domain.pets.Pet

interface PetRepository {
  fun create(pet: Pet): Either<PetErrors, Pet>
}
