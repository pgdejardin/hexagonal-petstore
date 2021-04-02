package org.example.infrastructure.pets.repository

import arrow.core.Either
import arrow.core.right
import org.example.domain.PetErrors
import org.example.domain.pets.Pet
import org.example.domain.pets.adapter.PetRepository
import java.util.*
import kotlin.collections.HashMap

fun Pet.toPetEntry() = PetEntry(id!!, name)

object InMemoryPetRepository : PetRepository {
  private val store: MutableMap<UUID, PetEntry> = HashMap()

  override fun create(pet: Pet): Either<PetErrors, Pet> {
    val petEntry = Either.catch { pet.toPetEntry() }.mapLeft { PetErrors.PetHasNoId }
    return when (petEntry) {
      is Either.Right -> {
        Either.catch { store[petEntry.value.id] = petEntry.value }.mapLeft { PetErrors.CannotSavePetInDB }
      }
      else -> {
        Unit.right()
      }
    }.map { pet }
  }
}

data class PetEntry(val id: UUID, val name: String)

