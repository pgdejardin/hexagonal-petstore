package org.example.domain.pets.adapter

import arrow.core.Either
import org.example.domain.PetErrors.PetProducerError
import org.example.domain.pets.Pet

interface PetProducer {
  fun newPetAdded(pet: Pet): Either<PetProducerError, Pet>
}
